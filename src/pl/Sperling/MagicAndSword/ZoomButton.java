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

import android.content.Context;
import org.anddev.andengine.engine.camera.ZoomCamera;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureManager;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

/**
 * User: pawel
 * Date: 05.12.12
 * Time: 11:39
 */
public class ZoomButton extends Sprite {

    private ZoomCamera camera;
    private Sprite zoomButtonSprite;
    private BitmapTextureAtlas zoomButtonTextureAtlas;
    private TextureRegion zoomButtonTextureRegion;

    public ZoomButton(float pX, float pY, TextureRegion pTextureRegion) {
        super(pX, pY, pTextureRegion);
    }

    /*public ZoomButton(ZoomCamera cam) {
        camera = cam;

    } */

    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public void loadResources(Context context, TextureManager textureManager) {
        zoomButtonTextureAtlas = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        zoomButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(zoomButtonTextureAtlas, context, "gfx/zombie-100pix.png", 0, 0);
        textureManager.loadTexture(zoomButtonTextureAtlas);

        //zoomButtonSprite = new Sprite(64, 64, zoomButtonTextureRegion);
    }

    public Sprite getZoomButtonSprite() {
        return zoomButtonSprite;
    }
}