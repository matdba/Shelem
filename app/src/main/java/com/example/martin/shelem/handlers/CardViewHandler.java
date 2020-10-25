package com.example.martin.shelem.handlers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.example.martin.shelem.R;
import com.example.martin.shelem.customViews.ImageViewCustom;
import com.example.martin.shelem.instances.CardPosition;
import com.example.martin.shelem.utils.UnitHandler;

public class CardViewHandler {
    private Activity activity;
    private RelativeLayout root;
    private ImageViewCustom[] myCardsImgs, leftOverCardsImgs;
    private ImageViewCustom[] playerTwoCardsImgs, playerThreeCardsImgs, playerFourCardsImgs;
    private ImageViewCustom[] currentPlayedCards;



    private UnitHandler unitHandler;
    private CardsHandler cardsHandler;
    private AnimationHandler animationHandler;


    private String[] myCardsStrs, leftOverCardsStrs;
    private CardPosition[] leftoverCardsPositions;
    private int cardWidth, cardHeight;
    private int screenWidthChangable, totalCards, totalAngle, xDelta, yDelta, initX, initY, initLeftMargin, initBottomMargin;
    private double cardImagesLength;
    private float anglePerCard, initRotation;
    private boolean isChooseLeftOverMode = true;

    private enum directions {down, right, up, left}



    private void init() {

        root = activity.findViewById(R.id.root);


        unitHandler = new UnitHandler(activity);
        animationHandler = new AnimationHandler(activity);
        cardsHandler = new CardsHandler();



        myCardsStrs = new String[12];
        myCardsImgs = new ImageViewCustom[12];

        leftOverCardsImgs = new ImageViewCustom[6];
        leftOverCardsStrs = new String[6];
        leftoverCardsPositions = new CardPosition[6];

        playerTwoCardsImgs = new ImageViewCustom[12];
        playerThreeCardsImgs = new ImageViewCustom[12];
        playerFourCardsImgs = new ImageViewCustom[12];

        currentPlayedCards = new ImageViewCustom[4];



        cardWidth = unitHandler.getPixels(70);
        cardHeight = unitHandler.getPixels(105);

        totalCards = myCardsImgs.length;
        totalAngle = 60;
        anglePerCard = totalAngle / (float) (totalCards - 1);

        addLeftoverCardsToScreen();
        addMyCardsToScreen();
        addOtherPlayersCardsToScreen(playerFourCardsImgs);
        addOtherPlayersCardsToScreen(playerThreeCardsImgs);
        addOtherPlayersCardsToScreen(playerTwoCardsImgs);



        myCardsStrs = cardsHandler.getPlayersCard(1);
        leftOverCardsStrs = cardsHandler.getLeftOvercards();





        for (int i = 0; i < leftOverCardsImgs.length ; i++) {
            int id = activity.getResources().getIdentifier(leftOverCardsStrs[i], "drawable", activity.getPackageName());
            leftOverCardsImgs[i].down.setImageDrawable(activity.getResources().getDrawable(id));
        }


        for (int i = 0; i < myCardsImgs.length ; i++) {
            int id = activity.getResources().getIdentifier(myCardsStrs[i], "drawable", activity.getPackageName());
            myCardsImgs[i].down.setImageDrawable(activity.getResources().getDrawable(id));
        }

        new Handler().postDelayed(() -> distributeOtherPlayersCards(playerTwoCardsImgs, directions.right), 1800);

        new Handler().postDelayed(() -> distributeOtherPlayersCards(playerThreeCardsImgs, directions.up), 3600);

        new Handler().postDelayed(() -> distributeOtherPlayersCards(playerFourCardsImgs, directions.left), 5400);

        new Handler().postDelayed(this::distributeMyCards, 7200);

        new Handler().postDelayed(() -> {
            sortMyCards();
            putAsideLeftoverCards(false);
        }, 9600);


    }





    public CardViewHandler(Activity activity) {
        this.activity = activity;
        init();
    }




    private void addMyCardsToScreen() {
        cardImagesLength = getMyCardsbetweenSpace(myCardsImgs.length);
        for (int i = 0; i < myCardsImgs.length; i++) {

            ImageViewCustom imageViewCustom = new ImageViewCustom(activity);

            RelativeLayout.LayoutParams layoutParams =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

            imageViewCustom.setLayoutParams(layoutParams);

            root.addView(imageViewCustom);

            myCardsImgs[i] = imageViewCustom;

            final int finalI = i;
            myCardsImgs[finalI].getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    if (myCardsImgs[finalI].getViewTreeObserver().isAlive()) {
                        myCardsImgs[finalI].getViewTreeObserver().removeOnPreDrawListener(this);
                    }

                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) myCardsImgs[finalI].getLayoutParams();
                    layoutParams.leftMargin = unitHandler.screenWidth / 2 - cardWidth / 2;
                    layoutParams.bottomMargin = unitHandler.screenHeight / 2 - cardHeight / 2;
                    layoutParams.width = cardWidth;
                    layoutParams.height = cardHeight;

                    myCardsImgs[finalI].setPivotX(cardWidth / 2);
                    myCardsImgs[finalI].setPivotY(cardHeight);
                    myCardsImgs[finalI].setGravity(Gravity.CENTER);

                    return false;
                }
            });
        }
    }






    private void distributeMyCards() {
        for (int i = 0; i < myCardsImgs.length; i++) {

            int finalI = i;
            new Handler().postDelayed(() -> {

                myCardsImgs[finalI].bringToFront();
                setMyCardsPositions(myCardsImgs[finalI], finalI, -30f + (anglePerCard * finalI));
                animationHandler.flipAnimation(myCardsImgs[finalI], 100);
                animationHandler.rotationAnimation(myCardsImgs[finalI], -30f + (anglePerCard * finalI), 100, null);
            }, 150 * (finalI + 1));
        }
    }





    private void setMyCardsPositions(ImageViewCustom view, int position, float rotation) {
        int bottomMargin = 0;
        int leftMargin = (int) (getMyFirstCardPosition() + position * cardImagesLength);

        if (rotation <= 0) {
            while (rotation > -((float) 30.0 + .1)) {
                bottomMargin += (Math.tan(Math.toRadians(Math.abs(rotation)))) * (unitHandler.getPixels(16));
                rotation -= anglePerCard;
            }
        } else {
            while (rotation < (float) 30.0 + .1) {
                bottomMargin += (Math.tan(Math.toRadians(Math.abs(rotation)))) * (unitHandler.getPixels(16));
                rotation += anglePerCard;
            }
        }

        animationHandler.bottomMarginAnimation(view, bottomMargin, 400, null);
        animationHandler.leftMarginAnimation(view, leftMargin, 400, null);
        animationHandler.rightMarginAnimation(view, unitHandler.screenWidth - cardWidth - leftMargin, 400, null);
    }





    private void sortMyCards() {
        int[] positions = new int[myCardsStrs.length];
        String[] sortedCardsStrs = cardsHandler.sortCards(myCardsStrs);
        ImageViewCustom[] sortedCardsImgs = new ImageViewCustom[myCardsImgs.length];

        for (int i = 0; i < myCardsStrs.length; i++) {
            for (int j = 0; j < myCardsStrs.length; j++) {
                if (sortedCardsStrs[i].equals(myCardsStrs[j]))
                    positions[i] = j;
            }
        }

        for (int i = 0; i < myCardsImgs.length; i++) {
            sortedCardsImgs[i] = myCardsImgs[positions[i]];
        }

        System.arraycopy(sortedCardsImgs, 0, myCardsImgs, 0, myCardsImgs.length);
        System.arraycopy(sortedCardsStrs, 0, myCardsStrs, 0, myCardsStrs.length);

        for (int i = 0; i < myCardsImgs.length; i++) {
            myCardsImgs[i].bringToFront();
            animationHandler.rotationAnimation(myCardsImgs[i], -30f + (anglePerCard * i), 400, null);
            setMyCardsPositions(myCardsImgs[i], i, -30f + (anglePerCard * i));
        }

    }





    @SuppressLint("ClickableViewAccessibility")
    private void sortMyCardsPositions(int num) {

        myCardsImgs[num].setOnTouchListener(null);
        ImageViewCustom[] temp = myCardsImgs;
        myCardsImgs = null;
        myCardsImgs = new ImageViewCustom[temp.length - 1];

        int j = 0;
        while (j < myCardsImgs.length && j != num) {
            myCardsImgs[j] = temp[j];
            j++;
        }
        j++;
        while (j < temp.length) {
            myCardsImgs[j - 1] = temp[j];
            j++;
        }

        handleCardsTouch();


        if (myCardsImgs.length == 1) {
            animationHandler.rotationAnimation(myCardsImgs[0], 0, 400, null);
            animationHandler.leftMarginAnimation(myCardsImgs[0], unitHandler.screenWidth / 2 - unitHandler.getPixels(40), 400, null);
        } else {
            anglePerCard = totalAngle / (float) (myCardsImgs.length - 1);
            cardImagesLength = getMyCardsbetweenSpace(myCardsImgs.length);
            for (int i = 0; i < myCardsImgs.length; i++) {
                animationHandler.rotationAnimation(myCardsImgs[i], -30f + (anglePerCard * i), 400, null);
                setMyCardsPositions(myCardsImgs[i], i, -30f + (anglePerCard * i));
            }
        }

    }




    @SuppressLint("ClickableViewAccessibility")
    private void disableMyCardsTouch() {
        for (ImageViewCustom myCardsImg : myCardsImgs) {
            myCardsImg.setOnTouchListener((v, event) -> {
                v.setOnTouchListener(myCardsImg.mOnTouchListener);
                return false;
            });
        }
    }





    private void bringToFrontHandlerMyCards() {
        for (ImageViewCustom imageViewCustom : myCardsImgs) {
            imageViewCustom.bringToFront();
        }
    }





    private int getMyCardsbetweenSpace(int cardsNumber) {
        int imageLength = (unitHandler.screenWidth - screenWidthChangable - cardWidth - cardHeight) / (cardsNumber - 1);
        screenWidthChangable += unitHandler.screenWidth / 22;
        return imageLength;
    }





    private int getMyFirstCardPosition() {
        return (cardHeight / 2) + (screenWidthChangable / 2) - (unitHandler.screenWidth / 44);
    }






    private void addLeftoverCardsToScreen() {
        for (int i = 0; i < 6; i++) {

            ImageViewCustom imageViewCustom = new ImageViewCustom(activity);

            RelativeLayout.LayoutParams layoutParams =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

            imageViewCustom.setLayoutParams(layoutParams);

            root.addView(imageViewCustom);

            leftOverCardsImgs[i] = imageViewCustom;

            final int finalI = i;
            leftOverCardsImgs[finalI].getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    if (leftOverCardsImgs[finalI].getViewTreeObserver().isAlive()) {
                        leftOverCardsImgs[finalI].getViewTreeObserver().removeOnPreDrawListener(this);
                    }

                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) leftOverCardsImgs[finalI].getLayoutParams();
                    layoutParams.leftMargin = unitHandler.screenWidth / 2 - cardWidth / 2;
                    layoutParams.bottomMargin = unitHandler.screenHeight / 2 - cardHeight / 2;
                    layoutParams.width = cardWidth;
                    layoutParams.height = cardHeight;

                    leftOverCardsImgs[finalI].setPivotX(cardWidth / 2);
                    leftOverCardsImgs[finalI].setPivotY(cardHeight);

                    return false;
                }
            });
        }
    }



    public void putAsideLeftoverCards(boolean flip) {

        for (int i = leftOverCardsImgs.length - 1; i >= 0; i--) {

            int finalI = i;



            new Handler().postDelayed(() -> {
                int leftMargin = unitHandler.screenWidth - cardWidth - unitHandler.getPixels(16);
                int bottomMargin = unitHandler.screenHeight / 2 + unitHandler.getPixels(100);

                leftoverCardsPositions[finalI] = new CardPosition(leftMargin, bottomMargin);

                if (flip) {
                    animationHandler.flipBackAnimation(leftOverCardsImgs[finalI], 100);
                    disableLeftoverCardsTouch();
                    disableMyCardsTouch();
                    handleCardsTouch();
                    isChooseLeftOverMode = false;
                }
                animationHandler.scaleAnimation(leftOverCardsImgs[finalI], .8f, .8f, 600);
                animationHandler.topMarginAnimation(leftOverCardsImgs[finalI], - unitHandler.screenHeight - bottomMargin - cardHeight, 600, null);
                animationHandler.bottomMarginAnimation(leftOverCardsImgs[finalI], bottomMargin, 600, null);
                animationHandler.leftMarginAnimation(leftOverCardsImgs[finalI], leftMargin, 600, null);
                animationHandler.rightMarginAnimation(leftOverCardsImgs[finalI],unitHandler.screenWidth - leftMargin - cardWidth, 600, null);
            }, 150 * (leftOverCardsImgs.length - finalI));

        }

    }






    public void distributeLeftoverCards() {

        for (int i = leftOverCardsImgs.length - 1; i >= 0; i--) {

            int finalI = i;
            new Handler().postDelayed(() -> {

                int space = (int) ((unitHandler.screenWidth - unitHandler.getPixels(160) - 2.4 * cardWidth) / 2);
                int leftMargin = (int) (finalI % 3 * ((.8 * cardWidth) + space)) + unitHandler.getPixels(75);
                int bottomMargin;

                if (finalI < 3) {
                    bottomMargin = unitHandler.screenHeight / 2 + unitHandler.getPixels(50);
                } else {
                    bottomMargin = unitHandler.screenHeight / 2 - unitHandler.getPixels(50);
                }

                leftoverCardsPositions[finalI] = new CardPosition(leftMargin, bottomMargin);

                animationHandler.flipAnimation(leftOverCardsImgs[finalI], 100);
                animationHandler.scaleAnimation(leftOverCardsImgs[finalI], .8f, .8f, 400);
                animationHandler.bottomMarginAnimation(leftOverCardsImgs[finalI], bottomMargin, 400, null);
                animationHandler.leftMarginAnimation(leftOverCardsImgs[finalI], leftMargin, 400, null);

            }, 150 * (leftOverCardsImgs.length - finalI));

        }

    }





    private void checkWhichLeftoverCardChosen(View view, int position, float initRotation, int initLeftMargin, int initBottomMargin) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        boolean positioned = false;

        for (int i = 0; i < leftoverCardsPositions.length; i++) {
            if (layoutParams.leftMargin > leftoverCardsPositions[i].getLeft() - unitHandler.getPixels(28)
                    && layoutParams.leftMargin < leftoverCardsPositions[i].getLeft() + unitHandler.getPixels(28)
                    && layoutParams.bottomMargin > leftoverCardsPositions[i].getBottom() - unitHandler.getPixels(42)
                    && layoutParams.bottomMargin < leftoverCardsPositions[i].getBottom() + unitHandler.getPixels(42)) {

                positioned = true;
                animationHandler.bottomMarginAnimation(view, leftoverCardsPositions[i].getBottom(), 300, null);
                animationHandler.leftMarginAnimation(view, leftoverCardsPositions[i].getLeft(), 300, null);
                animationHandler.scaleAnimation(view, .8f, .8f, 300);
                animationHandler.scaleAnimation(leftOverCardsImgs[i], 1, 1, 300);

                ImageViewCustom temp = myCardsImgs[position];
                myCardsImgs[position] = leftOverCardsImgs[i];
                leftOverCardsImgs[i] = temp;

                String tempStr = myCardsStrs[position];
                myCardsStrs[position] = leftOverCardsStrs[i];
                leftOverCardsStrs[i] = tempStr;

                sortMyCards();
                disableLeftoverCardsTouch();
                handleCardsTouch();

            }
        }

        if (!positioned) {
            bringToFrontHandlerMyCards();
            animationHandler.rotationAnimation(myCardsImgs[position], initRotation, 300, null);
            animationHandler.bottomMarginAnimation(myCardsImgs[position], initBottomMargin, 300, null);
            animationHandler.leftMarginAnimation(myCardsImgs[position], initLeftMargin, 300, null);
            animationHandler.rightMarginAnimation(myCardsImgs[position], unitHandler.screenWidth - cardWidth - initLeftMargin, 300, null);
        }
    }





    @SuppressLint("ClickableViewAccessibility")
    private void disableLeftoverCardsTouch() {
        for (ImageViewCustom leftOverCardsImg : leftOverCardsImgs) {
            leftOverCardsImg.setOnTouchListener(null);
        }
    }






    private void addOtherPlayersCardsToScreen(ImageViewCustom[] otherPlayersCardsImgs) {

        for (int i = 0; i < otherPlayersCardsImgs.length; i++) {

            ImageViewCustom imageViewCustom = new ImageViewCustom(activity);

            RelativeLayout.LayoutParams layoutParams =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

            imageViewCustom.setLayoutParams(layoutParams);

            root.addView(imageViewCustom);

            otherPlayersCardsImgs[i] = imageViewCustom;

            int finalI = i;
            otherPlayersCardsImgs[i].getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    if (otherPlayersCardsImgs[finalI].getViewTreeObserver().isAlive()) {
                        otherPlayersCardsImgs[finalI].getViewTreeObserver().removeOnPreDrawListener(this);
                    }

                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) otherPlayersCardsImgs[finalI].getLayoutParams();
                    layoutParams.leftMargin = unitHandler.screenWidth / 2 - cardWidth / 2;
                    layoutParams.bottomMargin = unitHandler.screenHeight / 2 - cardHeight / 2;
                    layoutParams.width = cardWidth;
                    layoutParams.height = cardHeight;
                    otherPlayersCardsImgs[finalI].setLayoutParams(layoutParams);

                    otherPlayersCardsImgs[finalI].setPivotX(cardWidth / 2);
                    otherPlayersCardsImgs[finalI].setPivotY(cardHeight);
                    otherPlayersCardsImgs[finalI].setGravity(Gravity.CENTER);

                    return false;
                }
            });

        }

        for (int i = otherPlayersCardsImgs.length - 1; i >= 0 ; i--) {
            otherPlayersCardsImgs[i].bringToFront();
        }
    }






    public void distributeOtherPlayersCards(ImageViewCustom[] otherPlayersCardsImgs, directions direction) {

        int leftMargin = 0;
        int bottomMargin = 0;

        switch (direction) {
            case right:
                leftMargin = unitHandler.screenWidth + cardWidth;
                bottomMargin = unitHandler.screenHeight / 2 - cardHeight / 2;
                break;
            case up:
                leftMargin = unitHandler.screenWidth / 2 - cardWidth / 2;
                bottomMargin = unitHandler.screenHeight + cardHeight;
                break;
            case left:
                leftMargin = - cardWidth;
                bottomMargin = unitHandler.screenHeight / 2 - cardHeight / 2;
                break;
        }


        for (int i = otherPlayersCardsImgs.length - 1; i >= 0; i--) {
            int finalLeftMargin = leftMargin;
            int finalBottomMargin = bottomMargin;
            int finalI = i;
            new Handler().postDelayed(() -> {
                animationHandler.topMarginAnimation(otherPlayersCardsImgs[finalI], - unitHandler.screenHeight - finalBottomMargin - cardHeight, 800, null);
                animationHandler.bottomMarginAnimation(otherPlayersCardsImgs[finalI], finalBottomMargin, 800, null);
                animationHandler.leftMarginAnimation(otherPlayersCardsImgs[finalI], finalLeftMargin, 600, null);
                animationHandler.rightMarginAnimation(otherPlayersCardsImgs[finalI],unitHandler.screenWidth - finalLeftMargin - cardWidth, 600, null);
            }, 150 * (finalI + 1));
        }
    }





    private void myCardPlayed(ImageViewCustom view, int position, float initRotation, int initLeftMargin, int initBottomMargin) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();

        if (layoutParams.bottomMargin >= initBottomMargin + unitHandler.getPixels(60)) {
            for (int i = 0; i < currentPlayedCards.length; i++) {
                if (currentPlayedCards[i] == null) {
                    currentPlayedCards[i] = view;
                    break;
                }
            }

            int leftMargin = (int) (unitHandler.screenWidth / 2 - (.5 * cardWidth));
            int bottomMargin = unitHandler.screenHeight / 2 - (cardHeight);
            animationHandler.bottomMarginAnimation(myCardsImgs[position], bottomMargin, 300, null);
            animationHandler.leftMarginAnimation(myCardsImgs[position], leftMargin, 300, null);
            animationHandler.scaleAnimation(myCardsImgs[position], .8f, .8f, 300);
            sortMyCardsPositions(position);
            otherPlayersCardPlayed(directions.right, 2, playerTwoCardsImgs, 1);
            new Handler().postDelayed(() -> otherPlayersCardPlayed(directions.up, 3, playerThreeCardsImgs, 2), 1000);
            new Handler().postDelayed(() -> otherPlayersCardPlayed(directions.left, 4, playerFourCardsImgs, 3), 2000);
            new Handler().postDelayed(() -> gatherCardsForWinningPlayer(directions.down), 3000);

        } else {

            bringToFrontHandlerMyCards();
            animationHandler.rotationAnimation(myCardsImgs[position], initRotation, 300, null);
            animationHandler.bottomMarginAnimation(myCardsImgs[position], initBottomMargin, 300, null);
            animationHandler.leftMarginAnimation(myCardsImgs[position], initLeftMargin, 300, null);
            animationHandler.rightMarginAnimation(myCardsImgs[position], unitHandler.screenWidth - cardWidth - initLeftMargin, 300, null);

        }
    }








    private void otherPlayersCardPlayed(directions direction, int playerNumber, ImageViewCustom[] cardsImgs, int position) {
        for (int i = 0; i < currentPlayedCards.length; i++) {
            if (currentPlayedCards[i] == null) {
                currentPlayedCards[i] = cardsImgs[position];
                break;
            }
        }

        String[] playerCardsStr = cardsHandler.getPlayersCard(playerNumber);
        int id = activity.getResources().getIdentifier(playerCardsStr[position], "drawable", activity.getPackageName());
        cardsImgs[position].down.setImageDrawable(activity.getResources().getDrawable(id));
        int leftMargin = 0, bottomMargin = 0;

        switch (direction) {
            case right:
                leftMargin = (int) (unitHandler.screenWidth / 2 + (.5 * cardWidth));
                bottomMargin = (int) (unitHandler.screenHeight / 2 - (.5 * cardHeight));
                break;
            case up:
                leftMargin = (int) (unitHandler.screenWidth / 2 - (.5 * cardWidth));
                bottomMargin = unitHandler.screenHeight / 2;
                break;
            case left:
                leftMargin = (int) (unitHandler.screenWidth / 2 - (1.5 * cardWidth));
                bottomMargin = (int) (unitHandler.screenHeight / 2 - (.5 * cardHeight));
                break;
        }

        animationHandler.flipAnimation(cardsImgs[position], 300);
        animationHandler.bottomMarginAnimation(cardsImgs[position], bottomMargin, 600, null);
        animationHandler.topMarginAnimation(cardsImgs[position], unitHandler.screenHeight - cardHeight - bottomMargin, 600, null);
        animationHandler.leftMarginAnimation(cardsImgs[position], leftMargin, 600, null);
        animationHandler.rightMarginAnimation(cardsImgs[position], unitHandler.screenWidth - cardWidth - leftMargin, 600, null);
        animationHandler.scaleAnimation(cardsImgs[position], .8f, .8f, 600);
    }







    private void gatherCardsForWinningPlayer(directions direction) {
        int leftMargin = 0, bottomMargin = 0;

        switch (direction) {
            case right:
                leftMargin = unitHandler.screenWidth + cardWidth;
                bottomMargin = unitHandler.screenHeight / 2 - cardHeight / 2;
                break;
            case up:
                leftMargin = unitHandler.screenWidth / 2 - cardWidth / 2;
                bottomMargin = unitHandler.screenHeight + cardHeight;
                break;
            case left:
                leftMargin = - cardWidth;
                bottomMargin = unitHandler.screenHeight / 2 - cardHeight / 2;
                break;
            case down:
                leftMargin = (int) (unitHandler.screenWidth / 2 - (1.5 * cardWidth));
                bottomMargin = - cardHeight;
                break;
        }

        for (ImageViewCustom currentPlayedCard : currentPlayedCards) {
            animationHandler.topMarginAnimation(currentPlayedCard, -unitHandler.screenHeight - bottomMargin - cardHeight, 600, null);
            animationHandler.bottomMarginAnimation(currentPlayedCard, bottomMargin, 600, null);
            animationHandler.leftMarginAnimation(currentPlayedCard, leftMargin, 600, null);
            animationHandler.rightMarginAnimation(currentPlayedCard, unitHandler.screenWidth - leftMargin - cardWidth, 600, null);
            animationHandler.scaleAnimation(currentPlayedCard, 1, 1, 600);
            currentPlayedCard = null;
        }



    }









    @SuppressLint("ClickableViewAccessibility")
    public void handleCardsTouch() {

        for (int i = 0; i < myCardsImgs.length; i++) {
            final int finalI = i;
            myCardsImgs[i].setOnTouchListener((v, event) -> {
                int pointerIndex = event.getActionIndex();

                int pointerId = event.getPointerId(pointerIndex);
                final int X = (int) event.getRawX();
                final int Y = (int) (unitHandler.screenHeight - event.getRawY());
                if (pointerId == 0) {


                    switch (event.getAction() & MotionEvent.ACTION_MASK) {

                        case MotionEvent.ACTION_DOWN:
                            RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                            initLeftMargin = lParams.leftMargin;
                            initBottomMargin = lParams.bottomMargin;
                            initRotation = myCardsImgs[finalI].getRotation();
                            myCardsImgs[finalI].bringToFront();
                            initX = X;
                            initY = Y;
                            xDelta = X - cardWidth / 2;
                            yDelta = Y - cardHeight / 2;
                            myCardsImgs[finalI].setRotation(0);
                            lParams.leftMargin = X - cardWidth / 2;
                            lParams.rightMargin = (unitHandler.screenWidth - X) - cardWidth / 2;
                            lParams.bottomMargin = Y - cardHeight / 2;
                            v.setLayoutParams(lParams);
                            break;



                        case MotionEvent.ACTION_UP:

                            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            new Handler().postDelayed(() ->
                                    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE), 400);

                            if (isChooseLeftOverMode) {
                                checkWhichLeftoverCardChosen(v, finalI, initRotation, initLeftMargin, initBottomMargin);
                            } else {
                                myCardPlayed(myCardsImgs[finalI], finalI, initRotation, initLeftMargin, initBottomMargin);
                            }


                            break;



                        case MotionEvent.ACTION_MOVE:

                            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                            layoutParams.leftMargin = (X - initX) + xDelta;
                            layoutParams.bottomMargin = (Y - initY) + yDelta;
                            layoutParams.rightMargin = xDelta - X;
                            layoutParams.topMargin = yDelta - Y;
                            v.setLayoutParams(layoutParams);
                            break;

                    }
                }

                activity.findViewById(android.R.id.content).getRootView();
                return true;
            });
        }
    }

}
