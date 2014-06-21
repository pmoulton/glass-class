package glass.such.classfeed.Data;

import android.content.Context;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

import com.google.android.glass.timeline.DirectRenderingCallback;

public class FeedDrawer implements DirectRenderingCallback {

    private SurfaceHolder mHolder;
    private boolean mRenderingPaused;
    private RenderThread mRenderThread;
    private static final long FRAME_TIME_MILLIS = 1000;

    private final FeedView mView;
    private final FeedView.ChangeListener mListener = new FeedView.ChangeListener() {
        @Override
        public void onChange() {
            if (mHolder != null) {
                draw();
            }
        }
    };

    public FeedDrawer(Context context) {
        mView = new FeedView(context);
        mView.setListener(mListener);
    }

    public FeedDrawer(FeedView view) {
        mView = view;
        mView.setListener(mListener);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Measure and layout the view with the canvas dimensions.
        final int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        final int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);

        mView.measure(measuredWidth, measuredHeight);
        mView.layout(0, 0, mView.getMeasuredWidth(), mView.getMeasuredHeight());
        updateRendering();

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // The creation of a new Surface implicitly resumes the rendering.
        mRenderingPaused = false;
        mHolder = holder;
        updateRendering();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mHolder = null;
        updateRendering();
    }

    @Override
    public void renderingPaused(SurfaceHolder holder, boolean paused) {
        mRenderingPaused = paused;
        updateRendering();
    }

    private void updateRendering() {
        boolean shouldRender = (mHolder != null) && !mRenderingPaused;
        boolean rendering = mRenderThread != null;

        if (shouldRender != rendering) {
            if (shouldRender) {
                mRenderThread = new RenderThread();
                mRenderThread.start();
            } else {
                mRenderThread.quit();
                mRenderThread = null;
            }
        }
    }

    public void draw() {
        Log.d("FeedDrawer", "draw");
        if (!mRenderingPaused && mHolder != null) {
            Canvas canvas;
            try {
                canvas = mHolder.lockCanvas();
            } catch (Exception e) {
                return;
            }
            if (canvas != null) {
                mView.draw(canvas);
                mHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private class RenderThread extends Thread {
        private boolean mShouldRun;

        /**
         * Initializes the background rendering thread.
         */
        public RenderThread() {
            mShouldRun = true;
        }

        /**
         * Returns true if the rendering thread should continue to run.
         *
         * @return true if the rendering thread should continue to run
         */
        private synchronized boolean shouldRun() {
            return mShouldRun;
        }

        /**
         * Requests that the rendering thread exit at the next
         opportunity.
         */
        public synchronized void quit() {
            mShouldRun = false;
        }

        @Override
        public void run() {
            while (shouldRun()) {
                draw();
                SystemClock.sleep(FRAME_TIME_MILLIS);
            }
        }
    }
}
