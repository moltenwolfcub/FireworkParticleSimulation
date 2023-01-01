package com.moltenwolfcub.firework.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class ColoredPoint extends Vector2 {
    private Color color;
    
	public ColoredPoint () {
        super();
        this.color = Color.WHITE;
	}

	public ColoredPoint (float x, float y) {
        super(x, y);
        this.color = Color.WHITE;
	}

	public ColoredPoint (Vector2 v) {
		super(v);
        this.color = Color.WHITE;
	}
    
	public ColoredPoint (Color c) {
        super();
        this.color = c;
	}

	public ColoredPoint (Color c, float x, float y) {
        super(x, y);
        this.color = c;
	}

	public ColoredPoint (Color c, Vector2 v) {
		super(v);
        this.color = c;
	}

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
