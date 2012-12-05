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

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Display;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.ZoomCamera;
import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.util.FPSCounter;
import org.anddev.andengine.extension.input.touch.controller.MultiTouch;
import org.anddev.andengine.extension.input.touch.controller.MultiTouchController;
import org.anddev.andengine.extension.input.touch.detector.PinchZoomDetector;
import org.anddev.andengine.extension.input.touch.exception.MultiTouchException;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.input.touch.detector.ClickDetector;
import org.anddev.andengine.input.touch.detector.SurfaceScrollDetector;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

public class MainActivity extends BaseGameActivity implements Scene.IOnSceneTouchListener, ClickDetector.IClickDetectorListener {

    private int cameraWidth, cameraHeight;
    private ZoomCamera camera;
    private Font font;
    private SurfaceScrollDetector scrollDetector;
    private PinchZoomDetector zoomDetector;
    private ClickDetector clickDetector;
    private MapScroller mapScroller;
    private BitmapTextureAtlas fontTextureAtlas;
    private ChangeableText fpsText;

    private BitmapTextureAtlas tileTextureAtlas;
    private TextureRegion tileTextureRegion;
    private Sprite[][] tiles;

    //private BitmapTextureAtlas zoomButtonTextureAtlas;
    //private TextureRegion zoomButtonTextureRegion;
    //private Sprite zoomButton;
    
    private ZoomButton zoomButton;

    @Override
    public Engine onLoadEngine() {
        final Display display = getWindowManager().getDefaultDisplay();

        cameraWidth = display.getWidth();
        cameraHeight = display.getHeight();

        camera = new ZoomCamera(-1 * 64, 0, cameraWidth, cameraHeight);

        camera.setBounds(-3 * 64, 43 * 64, -3 * 64, 43 * 64);
        camera.setBoundsEnabled(true);
        
        mapScroller = new MapScroller(camera);

        final Engine engine = new Engine(new EngineOptions(true, EngineOptions.ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(cameraWidth, cameraHeight), camera));

        if(MultiTouch.isSupported(this)) {
            Settings.setMultitouch(true);
            try {
                engine.setTouchController(new MultiTouchController());
            } catch (MultiTouchException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } else {
            Settings.setMultitouch(false);
        }
        
        zoomButton = new ZoomButton(camera);

        return engine;
    }

    @Override
    public void onLoadResources() {
        fontTextureAtlas = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        font = new Font(fontTextureAtlas, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32, true, Color.RED);
        mEngine.getTextureManager().loadTexture(fontTextureAtlas);
        mEngine.getFontManager().loadFont(font);

        tileTextureAtlas = new BitmapTextureAtlas(64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        tileTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(tileTextureAtlas, this, "gfx/ui_ball_1.png", 0, 0);
        mEngine.getTextureManager().loadTexture(tileTextureAtlas);

        //zoomButtonTextureAtlas = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        //zoomButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(zoomButtonTextureAtlas, this, "gfx/zombie-100pix.png", 0, 0);
        //mEngine.getTextureManager().loadTexture(zoomButtonTextureAtlas);
        
        zoomButton.loadResources(this, mEngine.getTextureManager());
    }

    @Override
    public Scene onLoadScene() {
        Scene scene = new Scene();
        scene.setOnAreaTouchTraversalFrontToBack();

        scrollDetector = new SurfaceScrollDetector(mapScroller);
        scrollDetector.setEnabled(true);
        
        clickDetector = new ClickDetector(this);

        fpsText = new ChangeableText(10, 10, font, "FPS: 0", 30);
        //scene.attachChild(fpsText);

        //zoomButton = new Sprite(64, 64, zoomButtonTextureRegion);

        HUD hud = new HUD();
        hud.attachChild(fpsText);
        hud.attachChild(zoomButton.getZoomButtonSprite());
        //hud.attachChild(zoomButton);

        mEngine.getCamera().setHUD(hud);

        final FPSCounter fpsCounter = new FPSCounter();
        mEngine.registerUpdateHandler(fpsCounter);

        scene.registerUpdateHandler(new TimerHandler(1 / 20.0f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                fpsText.setText("FPS: " + fpsCounter.getFPS());
            }
        }));

        tiles = new Sprite[20][20];
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 20; j++) {
                tiles[i][j] = new Sprite(i * 64, j * 64, tileTextureRegion);

                scene.attachChild(tiles[i][j]);
            }
        }

        if(Settings.isMultitouch()) {
            try {
                zoomDetector = new PinchZoomDetector(mapScroller);
            } catch (MultiTouchException e) {
                zoomDetector = null;
            }
        } else {
            zoomDetector = null;
        }

        scene.setOnSceneTouchListener(this);
        scene.setTouchAreaBindingEnabled(true);
        scene.setOnSceneTouchListenerBindingEnabled(true);

        return scene;
    }

    @Override
    public void onLoadComplete() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if(Settings.isMultitouch()) {
            zoomDetector.onTouchEvent(pSceneTouchEvent);

            if(zoomDetector.isZooming()) {
                scrollDetector.setEnabled(false);
            } else {
                if(pSceneTouchEvent.isActionDown()) {
                    scrollDetector.setEnabled(true);
                }
                scrollDetector.onTouchEvent(pSceneTouchEvent);
            }
        } else {
            scrollDetector.onTouchEvent(pSceneTouchEvent);
        }

        clickDetector.onTouchEvent(pSceneTouchEvent);

        return true;
    }

    @Override
    public void onClick(ClickDetector pClickDetector, TouchEvent pTouchEvent) {

    }
}
