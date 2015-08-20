package net.i2p.android.ext.floatingactionbutton.sample_animated;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.i2p.android.ext.floatingactionbutton.FloatingActionButton;
import net.i2p.android.ext.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
    StringAdapter adapter = new StringAdapter(this, getSampleList());
    rv.setAdapter(adapter);

    RecyclerView.LayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    rv.setLayoutManager(lm);

    final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.pink_icon);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(MainActivity.this, "Clicked pink Floating Action Button", Toast.LENGTH_SHORT).show();
      }
    });

    rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (dy > 0) {
          fab.hide();
        } else {
          fab.show();
        }
      }
    });

    final FloatingActionsMenu fam = (FloatingActionsMenu) findViewById(R.id.multiple_actions);

    rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (dy > 0) {
          fam.hide();
        } else {
          fam.show();
        }
      }
    });

    Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
    tb.setTitle(R.string.app_name);
    tb.setSubtitle("Sample animation");
    tb.setLogo(android.R.drawable.sym_def_app_icon);
    setSupportActionBar(tb);
  }

  private List<String> getSampleList() {
    List<String> items = new ArrayList<>();
    for (int i = 0; i < 100; i++)
      items.add("Lorem ipsum " + i);
    return items;
  }

  public class StringAdapter extends RecyclerView.Adapter<StringAdapter.ViewHolder> {

    List<String> mItems;
    Context mContext;
    LayoutInflater mLayoutInflater;

    public StringAdapter(Context context, List<String> items) {
      mItems = items;
      mContext = context;
      mLayoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View v = mLayoutInflater.inflate(R.layout.card_item, parent, false);
      ViewHolder vh = new ViewHolder(v);
      return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      String item = mItems.get(position);
      holder.tv.setText(item);
    }

    @Override
    public int getItemCount() {
      return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
      TextView tv;

      public ViewHolder(View view) {
        super(view);
        tv = (TextView) view.findViewById(R.id.tv);
      }
    }
  }

}
