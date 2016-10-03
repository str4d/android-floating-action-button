package net.i2p.android.ext.floatingactionbutton;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * Created by douglas on 20/08/15.
 */
public final class AnimateUtil {

  private static final int TRANSLATE_DURATION_MILLIS = 200;
  private static  Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

  /**
   * Check if version is greater than <code>Build.VERSION_CODES.HONEYCOMB</code><br><br>
   * Added by Douglas Junior http://github.com/douglasjunior <br>
   * Based on https://github.com/makovkastar/FloatingActionButton
   *
   * @return <code>true</code> if greater than <code>Build.VERSION_CODES.HONEYCOMB</code>
   */
  public static boolean hasHoneycombApi() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
  }

  /**
   * Return the margin bottom in pixels. <br><br>
   * Added by Douglas Junior http://github.com/douglasjunior <br>
   * Based on https://github.com/makovkastar/FloatingActionButton
   *
   * @return the margin bottom in pixels
   */
  public static int getMarginBottom(View view) {
    int marginBottom = 0;
    final ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
    if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
      marginBottom = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
    }
    return marginBottom;
  }

  /**
   * Toggle the Floating Action Button.<br><br>
   * Added by Douglas Junior http://github.com/douglasjunior <br>
   * Based on https://github.com/makovkastar/FloatingActionButton
   *
   * @param visible if <code>true</code> make visible
   * @param animate if <code>true</code> makes animation
   * @param force   if <code>true</code> force toggle
   */
  public static void toggle(final Animatable animatable, final boolean visible, final boolean animate, boolean force) {
    if (animatable.isHidden() == visible || force) {
      animatable.setHidden(!visible);
      int height = animatable.getView().getHeight();
      if (height == 0 && !force) {
        ViewTreeObserver vto = animatable.getView().getViewTreeObserver();
        if (vto.isAlive()) {
          vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
              ViewTreeObserver currentVto = animatable.getView().getViewTreeObserver();
              if (currentVto.isAlive()) {
                currentVto.removeOnPreDrawListener(this);
              }
              toggle(animatable, visible, animate, true);
              return true;
            }
          });
          return;
        }
      }
      int translationY = visible ? 0 : height + AnimateUtil.getMarginBottom(animatable.getView());
      if (animate) {
        ViewPropertyAnimator.animate(animatable.getView()).setInterpolator(mInterpolator)
            .setDuration(TRANSLATE_DURATION_MILLIS)
            .translationY(translationY);
      } else {
        ViewHelper.setTranslationY(animatable.getView(), translationY);
      }

      // On pre-Honeycomb a translated view is still clickable, so we need to disable clicks manually
      if (!AnimateUtil.hasHoneycombApi()) {
        animatable.getView().setClickable(visible);
      }
    }
  }
}
