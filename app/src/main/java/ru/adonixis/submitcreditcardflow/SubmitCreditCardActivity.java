package ru.adonixis.submitcreditcardflow;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import ru.adonixis.submitcreditcardflow.databinding.ActivitySubmitCreditCardBinding;

public class SubmitCreditCardActivity extends AppCompatActivity {

    private boolean showingGray = true;
    private AnimatorSet inSet;
    private AnimatorSet outSet;
    private ActivitySubmitCreditCardBinding activitySubmitCreditCardBinding;
    private Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activitySubmitCreditCardBinding = DataBindingUtil.setContentView(this, R.layout.activity_submit_credit_card);
        card = new Card();

        setSupportActionBar(activitySubmitCreditCardBinding.toolbar);

        View.OnClickListener onHelpClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SubmitCreditCardActivity.this, "The CVV Number (\"Card Verification Value\") is a 3 or 4 digit number on your credit and debit cards", Toast.LENGTH_LONG).show();
            }
        };

        activitySubmitCreditCardBinding.iconHelpGray.setOnClickListener(onHelpClickListener);
        activitySubmitCreditCardBinding.iconHelpBlue.setOnClickListener(onHelpClickListener);

        activitySubmitCreditCardBinding.inputEditCardNumber.addTextChangedListener(new TextWatcher() {

            private boolean lock;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    flipToBlue();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (lock || s.length() > 16) {
                    return;
                }
                lock = true;
                for (int i = 4; i < s.length(); i += 5) {
                    if (s.toString().charAt(i) != ' ') {
                        s.insert(i, " ");
                    }
                }
                lock = false;
            }
        });

        activitySubmitCreditCardBinding.inputEditExpiredDate.addTextChangedListener(new TextWatcher() {

            private boolean lock;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (lock || s.length() > 4) {
                    return;
                }
                lock = true;
                if (s.length() > 2 && s.toString().charAt(2) != '/') {
                    s.insert(2, "/");
                }
                lock = false;
            }
        });

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        PagerAdapter adapter = new MyPagerAdapter();
        activitySubmitCreditCardBinding.viewPager.setAdapter(adapter);
        activitySubmitCreditCardBinding.viewPager.setClipToPadding(false);
        activitySubmitCreditCardBinding.viewPager.setPadding(width / 4, 0, width / 4, 0);
        activitySubmitCreditCardBinding.viewPager.setPageMargin(width / 14);
        activitySubmitCreditCardBinding.viewPager.setPagingEnabled(false);
        activitySubmitCreditCardBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        updateProgressBar(25);
                        activitySubmitCreditCardBinding.inputEditCardNumber.setFocusableInTouchMode(true);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardHolder.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCvvCode.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardNumber.requestFocus();
                        return;
                    case 1:
                        updateProgressBar(50);
                        activitySubmitCreditCardBinding.inputEditCardNumber.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.setFocusableInTouchMode(true);
                        activitySubmitCreditCardBinding.inputEditCardHolder.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCvvCode.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.requestFocus();
                        return;
                    case 2:
                        updateProgressBar(75);
                        activitySubmitCreditCardBinding.inputEditCardNumber.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardHolder.setFocusableInTouchMode(true);
                        activitySubmitCreditCardBinding.inputEditCvvCode.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardHolder.requestFocus();
                        return;
                    case 3:
                        updateProgressBar(100);
                        activitySubmitCreditCardBinding.inputEditCardNumber.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardHolder.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCvvCode.setFocusableInTouchMode(true);
                        activitySubmitCreditCardBinding.inputEditCvvCode.requestFocus();
                        return;
                    case 4:
                        activitySubmitCreditCardBinding.inputEditCardNumber.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditExpiredDate.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCardHolder.setFocusable(false);
                        activitySubmitCreditCardBinding.inputEditCvvCode.setFocusable(false);
                        return;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    activitySubmitCreditCardBinding.viewPager.setCurrentItem(activitySubmitCreditCardBinding.viewPager.getCurrentItem() + 1);
                    handled = true;
                }
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    submit();
                    handled = true;
                }
                return handled;
            }
        };

        activitySubmitCreditCardBinding.inputEditCardNumber.setOnEditorActionListener(onEditorActionListener);
        activitySubmitCreditCardBinding.inputEditExpiredDate.setOnEditorActionListener(onEditorActionListener);
        activitySubmitCreditCardBinding.inputEditCardHolder.setOnEditorActionListener(onEditorActionListener);
        activitySubmitCreditCardBinding.inputEditCvvCode.setOnEditorActionListener(onEditorActionListener);

        activitySubmitCreditCardBinding.inputEditCardNumber.requestFocus();

        inSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_in);
        outSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_out);
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.input_layout_card_number;
                    break;
                case 1:
                    resId = R.id.input_layout_expired_date;
                    break;
                case 2:
                    resId = R.id.input_layout_card_holder;
                    break;
                case 3:
                    resId = R.id.input_layout_cvv_code;
                    break;
                case 4:
                    resId = R.id.space;
                    break;

            }
            return findViewById(resId);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }


        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    private void updateProgressBar(int progress) {
        ObjectAnimator animation = ObjectAnimator.ofInt(activitySubmitCreditCardBinding.progressHorizontal, "progress", progress);
        animation.setDuration(300);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    private void submit() {
        activitySubmitCreditCardBinding.viewPager.setCurrentItem(4);
        card.setCardNumber(activitySubmitCreditCardBinding.inputEditCardNumber.getText().toString());
        card.setExpiredDate(activitySubmitCreditCardBinding.inputEditExpiredDate.getText().toString());
        card.setCardHolder(activitySubmitCreditCardBinding.inputEditCardHolder.getText().toString());
        card.setCvvCode(activitySubmitCreditCardBinding.inputEditCvvCode.getText().toString());
        Toast.makeText(SubmitCreditCardActivity.this, card.toString(), Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                activitySubmitCreditCardBinding.inputLayoutCvvCode.setVisibility(View.INVISIBLE);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
                activitySubmitCreditCardBinding.labelSecureSubmission.setVisibility(View.VISIBLE);
                hideKeyboard(activitySubmitCreditCardBinding.inputEditCvvCode);
                activitySubmitCreditCardBinding.progressCircle.setVisibility(View.VISIBLE);
            }
        }, 300);
    }

    private void reset() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        activitySubmitCreditCardBinding.inputLayoutCvvCode.setVisibility(View.VISIBLE);
        activitySubmitCreditCardBinding.progressCircle.setVisibility(View.GONE);
        activitySubmitCreditCardBinding.labelSecureSubmission.setVisibility(View.GONE);
        flipToGray();
        activitySubmitCreditCardBinding.viewPager.setCurrentItem(0);
        activitySubmitCreditCardBinding.inputEditCardNumber.setText("");
        activitySubmitCreditCardBinding.inputEditExpiredDate.setText("");
        activitySubmitCreditCardBinding.inputEditCardHolder.setText("");
        activitySubmitCreditCardBinding.inputEditCvvCode.setText("");
        activitySubmitCreditCardBinding.inputEditCardNumber.requestFocus();
        showKeyboard(activitySubmitCreditCardBinding.inputEditCardNumber);
    }

    private void flipToGray() {
        if (!showingGray && !outSet.isRunning() && !inSet.isRunning()) {
            showingGray = true;

            activitySubmitCreditCardBinding.cardBlue.setCardElevation(0);
            activitySubmitCreditCardBinding.cardGray.setCardElevation(0);

            outSet.setTarget(activitySubmitCreditCardBinding.cardBlue);
            outSet.start();

            inSet.setTarget(activitySubmitCreditCardBinding.cardGray);
            inSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    activitySubmitCreditCardBinding.cardGray.setCardElevation(convertDpToPixel(12, SubmitCreditCardActivity.this));
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            inSet.start();
        }
    }

    private void flipToBlue() {
        if (showingGray && !outSet.isRunning() && !inSet.isRunning()) {
            showingGray = false;

            activitySubmitCreditCardBinding.cardGray.setCardElevation(0);
            activitySubmitCreditCardBinding.cardBlue.setCardElevation(0);

            outSet.setTarget(activitySubmitCreditCardBinding.cardGray);
            outSet.start();

            inSet.setTarget(activitySubmitCreditCardBinding.cardBlue);
            inSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    activitySubmitCreditCardBinding.cardBlue.setCardElevation(convertDpToPixel(12, SubmitCreditCardActivity.this));
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            inSet.start();
        }
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reset:
                reset();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
