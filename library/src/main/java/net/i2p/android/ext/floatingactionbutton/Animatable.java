package net.i2p.android.ext.floatingactionbutton;

import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by douglas on 20/08/15.
 */
interface Animatable {
  void setHidden(boolean hidden);

  public boolean isHidden();

  public void show();

  public void hide();

  public void show(boolean animate);

  public void hide(boolean animate);

  void toggle(final boolean visible, final boolean animate, boolean force);

  public View getView();
}
