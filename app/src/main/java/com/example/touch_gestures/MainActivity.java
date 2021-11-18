package com.example.touch_gestures;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private ScaleGestureDetector scaleDetector;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LinearLayout myView = (LinearLayout) findViewById(R.id.myView);
        myView.setOnTouchListener(new MyGesture(myView));

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnTouchListener(new MyScale(imageView));

    }

}



    //모든 제스처
    class MyGesture implements View.OnTouchListener {
        private View view;
        private ScaleGestureDetector scaleDetector;
        private GestureDetector gestureDetector;

        public MyGesture(View view) {
            this.view = view;

            initialGestures();
        }


        private void initialGestures() {
            scaleDetector = new ScaleGestureDetector(
                    view.getContext(),
                    new ScaleGestureDetector.SimpleOnScaleGestureListener() {

                        //확대,축소
                        private static final float MIN_ZOOM = 1.0f;
                        private static final float MAX_ZOOM = 4.0f;
                        private float scale = 1.0f;
                        private float lastScaleFactor = 0f;

                        @Override
                        public boolean onScale(ScaleGestureDetector detector) {

                            float scaleFactor = scaleDetector.getScaleFactor();
                            Log.i("test", "확대,축소");

                            if (lastScaleFactor == 0 || (Math.signum(scaleFactor) == Math.signum(lastScaleFactor))) {
                                scale *= scaleFactor;
                                scale = Math.max(MIN_ZOOM, Math.min(scale, MAX_ZOOM));
                                lastScaleFactor = scaleFactor;

                                view.setScaleX(scale);
                                view.setScaleY(scale);


                            } else {
                                lastScaleFactor = 0;
                            }
                            return super.onScale(detector);

                        }


                    });


            gestureDetector = new GestureDetector(
                    view.getContext(),
                    new GestureDetector.SimpleOnGestureListener() {

                        //스와이프 변수
                        private static final int SWIPE_THRESHOLD = 100;
                        private static final int SWIPE_VELOCITY_THRESHOLD = 100;


                        //화면이 눌린채 손가락이 가속해서 움직였다 떼어지는 경우
                        @Override
                        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                            Log.i("test", "onFling : 플링");
                            Toast.makeText(view.getContext(), "플링", Toast.LENGTH_SHORT).show();

                            //스와이프
                            boolean result = false;
                            try {
                                float diffY = e2.getY() - e1.getY();
                                float diffX = e2.getX() - e1.getX();
                                if (Math.abs(diffX) > Math.abs(diffY)) {
                                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                                        if (diffX > 0) {
                                            onSwipeRight();
                                        } else {
                                            onSwipeLeft();
                                        }
                                        result = true;
                                    }
                                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                                    if (diffY > 0) {
                                        onSwipeBottom();
                                    } else {
                                        onSwipeTop();
                                    }
                                    result = true;
                                }
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            return true;
                        }


                        public void onSwipeRight() {
                            Log.i("test", "오른쪽으로 스와이프");
                            Toast.makeText(view.getContext(), "right swipe", Toast.LENGTH_SHORT).show();

                            TextView textView = (TextView) view.findViewById(R.id.textView);
                            textView.setText("오른쪽으로 스와이프");
                        }

                        public void onSwipeLeft() {
                            Log.i("test", "왼쪽으로 스와이프");
                            Toast.makeText(view.getContext(), "left swipe", Toast.LENGTH_SHORT).show();

                            TextView textView = (TextView) view.findViewById(R.id.textView);
                            textView.setText("왼쪽으로 스와이프");


                        }

                        public void onSwipeTop() {
                            Log.i("test", "위로 스와이프");
                            Toast.makeText(view.getContext(), "top swipe", Toast.LENGTH_SHORT).show();

                            TextView textView = (TextView) view.findViewById(R.id.textView);
                            textView.setText("위로 스와이프");


                        }

                        public void onSwipeBottom() {
                            Log.i("test", "아래로 스와이프");
                            Toast.makeText(view.getContext(), "bottom swipe", Toast.LENGTH_SHORT).show();

                            TextView textView = (TextView) view.findViewById(R.id.textView);
                            textView.setText("아래로 스와이프");

                        }


                        //화면이 한 손가락으로 눌렸다 떼어지는 경우
                        @Override
                        public boolean onSingleTapUp(MotionEvent e) {
                            Log.i("test", "onSingleTapUp : 화면 한 손가락으로 눌렸다 떼어짐");
                            Toast.makeText(view.getContext(), "화면 한 손가락으로 눌렸다 떼어짐", Toast.LENGTH_SHORT).show();
                            return true;
                        }

                        //화면이 눌렸다 떼어지는 경우
                        @Override
                        public void onShowPress(MotionEvent e) {
                            Log.i("test", "onShowPress : 화면 눌렸다 떼어짐");
                            Toast.makeText(view.getContext(), "화면 눌렸다 떼어짐", Toast.LENGTH_SHORT).show();
                        }

                        //화면이 눌린채 일정한 속도와 방향으로 움직였다 떼어지는 경우
                        @Override
                        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                            Log.i("test", "onScroll : 스크롤");
                            Toast.makeText(view.getContext(), "스크롤", Toast.LENGTH_SHORT).show();
                            return true;
                        }

                        //화면을 손가락으로 오랫동안 눌렀을 경우
                        @Override
                        public void onLongPress(MotionEvent e) {
                            Log.i("test", "onLongPress : 오래 누름");
                            Toast.makeText(view.getContext(), "오래 누름", Toast.LENGTH_SHORT).show();
                        }


                        //화면이 눌렸을 때(일단 손 대면 무조건 발생)
                        @Override
                        public boolean onDown(MotionEvent e) {
                            Log.i("test", "onDrow : 화면 눌림");
                            Toast.makeText(view.getContext(), "화면 눌림", Toast.LENGTH_SHORT).show();
                            return true;
                        }

                        //두 번 터치
                        @Override
                        public boolean onDoubleTap(MotionEvent e) {
                            Log.i("test", "onDoubleTap : 두 번 터치");
                            Toast.makeText(view.getContext(), "두 번 터치", Toast.LENGTH_SHORT).show();
                            return super.onDoubleTap(e);
                        }

                        //두 번 터치 +DOWN, MOVE, UP 이벤트까지 모두 캐치 됨
                        @Override
                        public boolean onDoubleTapEvent(MotionEvent e) {
                            Log.i("test", "onDoubleTap : 두 번 터치 이벤트");
                            Toast.makeText(view.getContext(), "두 번 터치 이벤트", Toast.LENGTH_SHORT).show();
                            return super.onDoubleTapEvent(e);
                        }

                        //화면이 눌리고 다시 onDown 이벤트가 발생 하지 않는 경우
                        //확실히 한 번 터치되었다는 이벤트
                        @Override
                        public boolean onSingleTapConfirmed(MotionEvent e) {
                            Log.i("test", "onSingleTapConfirmed : 한 번 터치 확실");
                            Toast.makeText(view.getContext(), "한 번 터치 확실", Toast.LENGTH_SHORT).show();
                            return super.onSingleTapConfirmed(e);
                        }
                    });
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            int id[] = new int[3];
            int x[] = new int[3];
            int y[] = new int[3];
            String result;

            int pointer_count = event.getPointerCount(); //현재 터치 발생한 포인트 수를 얻는다.
            if (pointer_count > 3) pointer_count = 3; //4개 이상의 포인트를 터치했더라도 3개까지만 처리를 한다.


            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN: //한 개 포인트에 대한 DOWN을 얻을 때.
                    result = "싱글터치 : \n";
                    id[0] = event.getPointerId(0); //터치한 순간부터 부여되는 포인트 고유번호
                    x[0] = (int) (event.getX());
                    y[0] = (int) (event.getY());
                    result = "싱글터치 : \n";
                    result += "(" + x[0] + "," + y[0] + ")";
                    break;

                case MotionEvent.ACTION_POINTER_DOWN: //두 개 이상의 포인트에 대한 DOWN을 얻을 때.
                    result = "멀티터치 :\n";
                    for (int i = 0; i < pointer_count; i++) {
                        id[i] = event.getPointerId(i); //터치한 순간부터 부여되는 포인트 고유번호
                        x[i] = (int) (event.getX(i));
                        y[i] = (int) (event.getY(i));
                        result += "id[" + id[i] + "] (" + x[i] + "," + y[i] + ")\n";
                    }
                    break;

                case MotionEvent.ACTION_MOVE:
                    result = "멀티터치 MOVE:\n";
                    for (int i = 0; i < pointer_count; i++) {
                        id[i] = event.getPointerId(i);
                        x[i] = (int) (event.getX(i));
                        y[i] = (int) (event.getY(i));
                        result += "id[" + id[i] + "] (" + x[i] + "," + y[i] + ")\n";
                    }
                    break;
                case MotionEvent.ACTION_UP: //마지막 손가락이 화면을 떠날 때
                    result = "터치 UP";
                    break;

                case MotionEvent.ACTION_POINTER_UP: //손가락이 화면을 떠날 때 전송되지만 적어도 한 손가락은 여전히 화면을 만지고 있을 때
                    result = "일부 터치 UP";
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + (event.getAction() & MotionEvent.ACTION_MASK));
            }

            TextView textView = (TextView) view.findViewById(R.id.textView);
            textView.setText(result);

            gestureDetector.onTouchEvent(event);
            scaleDetector.onTouchEvent(event);


            return true;
        }


    }





    //확대 제스처
    class MyScale implements View.OnTouchListener {
        private View view;
        private ScaleGestureDetector scaleDetector;


        public MyScale(View view) {
            this.view = view;

            initialGestures();
        }


        private void initialGestures() {
            scaleDetector = new ScaleGestureDetector(
                    view.getContext(),
                    new ScaleGestureDetector.SimpleOnScaleGestureListener() {

                        //확대,축소
                        private static final float MIN_ZOOM = 1.0f;
                        private static final float MAX_ZOOM = 8.0f;
                        private float scale = 1.0f;
                        private float lastScaleFactor = 0f;

                        @Override
                        public boolean onScale(ScaleGestureDetector detector) {

                            float scaleFactor = scaleDetector.getScaleFactor();
                            Log.i("test", "확대,축소");

                            if (lastScaleFactor == 0 || (Math.signum(scaleFactor) == Math.signum(lastScaleFactor))) {
                                scale *= scaleFactor;
                                scale = Math.max(MIN_ZOOM, Math.min(scale, MAX_ZOOM));
                                lastScaleFactor = scaleFactor;

                                view.setScaleX(scale);
                                view.setScaleY(scale);


                            } else {
                                lastScaleFactor = 0;
                            }
                            return super.onScale(detector);
                        }

                    });

        }


        @Override
        public boolean onTouch(View v, MotionEvent event) {
            scaleDetector.onTouchEvent(event);

            int parentWidth = ((ViewGroup) v.getParent()).getWidth();    // 부모 View 의 Width
            int parentHeight = ((ViewGroup) v.getParent()).getHeight();    // 부모 View 의 Height

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // 뷰 누름
                float oldXvalue = event.getX();
                float oldYvalue = event.getY();

            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                // 뷰 이동 중
                v.setX(v.getX() + (event.getX()) - (v.getWidth() / 2));
                v.setY(v.getY() + (event.getY()) - (v.getHeight() / 2));

            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                // 뷰에서 손을 뗌

                if (v.getX() < 0) {
                    v.setX(0);
                } else if ((v.getX() + v.getWidth()) > parentWidth) {
                    v.setX(parentWidth - v.getWidth());
                }

                if (v.getY() < 0) {
                    v.setY(0);
                } else if ((v.getY() + v.getHeight()) > parentHeight) {
                    v.setY(parentHeight - v.getHeight());
                }

            }

            return true;

        }


    }


