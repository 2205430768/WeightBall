package com.example.model;

import org.jbox2d.dynamics.Body;
import com.example.mytableball2.GameView;

import android.graphics.Bitmap;

public class Ball extends MyBody{

	public Ball(Body body,Bitmap bitmap,GameView gameview) {
		super(body,bitmap,gameview);
	}

}
