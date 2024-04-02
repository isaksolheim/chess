package me.isak.chess

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import space.earlygrey.shapedrawer.ShapeDrawer

object Renderer {
    val spriteBatch: SpriteBatch by lazy { SpriteBatch() }
    val lightPixelDrawer: ShapeDrawer by lazy {
        val lightPixelTexture = Texture("lightpixel.png")
        val lightPixelRegion = TextureRegion(lightPixelTexture)
        ShapeDrawer(spriteBatch, lightPixelRegion)
    }
    val darkPixelDrawer: ShapeDrawer by lazy {
        val darkPixelTexture = Texture("darkpixel.png")
        val darkPixelRegion = TextureRegion(darkPixelTexture)
        ShapeDrawer(spriteBatch, darkPixelRegion)
    }
    val dotPixelDrawer: ShapeDrawer by lazy {
        val dotPixelTexture = Texture("Dot.png")
        val dotPixelRegion = TextureRegion(dotPixelTexture)
        ShapeDrawer(spriteBatch, dotPixelRegion)
    }
}