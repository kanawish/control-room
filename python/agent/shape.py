import asyncio
import logging
import os
from signal import SIGINT, SIGTERM
from typing import Optional, List

import cv2
import numpy as np
from livekit import rtc, api
from livekit.rtc import VideoFrame, VideoFrameEvent

from control_room.toolkit import load_config, log_room_activity, loop_on, first_track_queued_frame_looper


# Function to detect shape
def detect_shape(contour):
    shape = "unidentified"
    perimeter = cv2.arcLength(contour, True)
    approximation = cv2.approxPolyDP(contour, 0.04 * perimeter, True)

    if len(approximation) == 3:
        shape = "triangle"
    elif len(approximation) == 4:
        (x, y, w, h) = cv2.boundingRect(approximation)
        aspect_ratio = w / float(h)
        shape = "square" if 0.95 <= aspect_ratio <= 1.05 else "rectangle"
    elif len(approximation) == 5:
        shape = "pentagon"
    elif len(approximation) == 6:
        shape = "hexagon"
    else:
        shape = "circle"
    return shape


def create_windows(num) -> list[str]:
    names: list[str] = []
    for i in range(num):
        name = f'window_{i + 1}'
        cv2.namedWindow(name)
        cv2.moveWindow(name, 200 * i, 0)
        names.append(name)
    return names


async def handle_frame_event(frame_event: VideoFrameEvent, output_source: rtc.VideoSource):
    buffer: VideoFrame = frame_event.frame
    arr = np.frombuffer(buffer.data, dtype=np.uint8)
    arr = arr.reshape((buffer.height, buffer.width, 3))

    src_image = cv2.cvtColor(arr, cv2.COLOR_RGB2BGR)

    gray = cv2.cvtColor(src_image, cv2.COLOR_BGR2GRAY)
    cv2.imshow(windows[0], gray)
    blurred = cv2.GaussianBlur(gray, (7, 7), 0)
    cv2.imshow(windows[1], blurred)
    _, thresh = cv2.threshold(blurred, 120, 255, cv2.THRESH_BINARY_INV)
    cv2.imshow(windows[2], thresh)

    contours, _ = cv2.findContours(thresh, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

    dest_image = src_image

    for contour in contours:
        cv2.drawContours(dest_image, [contour], -1, (0, 255, 0), 2)
        # cv2.putText(dest_image, shape, (x, y - 10), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 255, 0), 2)
        cv2.imshow(windows[4], dest_image)

    frame = rtc.VideoFrame(
        buffer.width,
        buffer.height,
        rtc.VideoBufferType.RGB24,
        cv2.cvtColor(dest_image, cv2.COLOR_BGR2RGB).data
    )
    output_source.capture_frame(frame)

    cv2.waitKey(1)

# TODO: Get rid of top level preview stuff.
logging.basicConfig(level=logging.INFO)
loaded_config = load_config()

windows = create_windows(5)
cv2.startWindowThread()

if __name__ == "__main__":
    async def main(room: rtc.Room):
        return await first_track_queued_frame_looper(
            room=room,
            lk_id="foo",
            lk_name="foobar",
            handle_frame=handle_frame_event)

    loop_on(main)
    cv2.destroyAllWindows()
