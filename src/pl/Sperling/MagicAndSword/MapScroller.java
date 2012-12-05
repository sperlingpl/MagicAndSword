/*
 * This file is part of Magic And Sword.
 *
 * Magic And Sword is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Magic And Sword is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Magic And Sword; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package pl.Sperling.MagicAndSword;

import org.anddev.andengine.engine.camera.ZoomCamera;
import org.anddev.andengine.extension.input.touch.detector.PinchZoomDetector;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.input.touch.detector.ScrollDetector;

/**
 * User: pawel
 * Date: 05.12.12
 * Time: 09:11
 */
public class MapScroller implements ScrollDetector.IScrollDetectorListener, PinchZoomDetector.IPinchZoomDetectorListener {
    
    private ZoomCamera camera;
    private float zoomFactor;
    
    public MapScroller(ZoomCamera cam) {
        camera = cam;
    }
    
    @Override
    public void onScroll(ScrollDetector pScollDetector, TouchEvent pTouchEvent, float pDistanceX, float pDistanceY) {
        camera.offsetCenter(-pDistanceX, -pDistanceY);
    }

    @Override
    public void onPinchZoomStarted(PinchZoomDetector pPinchZoomDetector, TouchEvent pSceneTouchEvent) {
        zoomFactor = camera.getZoomFactor();
    }

    @Override
    public void onPinchZoom(PinchZoomDetector pPinchZoomDetector, TouchEvent pTouchEvent, float pZoomFactor) {
        camera.setZoomFactor(zoomFactor * pZoomFactor);
    }

    @Override
    public void onPinchZoomFinished(PinchZoomDetector pPinchZoomDetector, TouchEvent pTouchEvent, float pZoomFactor) {
        camera.setZoomFactor(zoomFactor * pZoomFactor);
    }
}
