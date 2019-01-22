/*
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 */

package com.tibco.xpd.processwidget.policies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Animation infrastructure. Animators works as a consumer of work queue.
 * Animations can be added and removed but it will create one thread to process
 * all animations and call method 'step' on them.
 * 
 * @author Wojciech Zurek (wzurek@tibco.com)
 */
public class AnimatorService implements Runnable {

    /** wait time between steps on the animation */
    private static final int STEP_WAIT_TIME = 80;

    /**
     * Singleton instance of the animator
     */
    public static final AnimatorService animator = new AnimatorService();

    /**
     * Working thread, can be null if there is no animations to proceed.
     */
    private Thread worker;

    /**
     * Interface for animations
     */
    public interface FigureAnimation {
        boolean step();
    }

    /**
     * List of animation to process, if empty worker thread can be stopped
     */
    private List animations = new ArrayList();

    /**
     * Add new animation to animator. If any animation require UI thread, this
     * method should not be called from UI thread. If the animation alredy exist
     * nothing is done.
     * 
     * @param animation
     */
    synchronized public void addAnimation(FigureAnimation animation) {
        if (!animations.contains(animation)) {
            animations.add(animation);
        }
        if (!animations.isEmpty() && worker == null) {
            worker = new Thread(this);
            worker.setName("AnimatorService"); //$NON-NLS-1$
            worker.start();
        } else if (!animations.isEmpty() && !worker.isAlive()) {
            worker = new Thread(this);
            worker.setName("AnimatorService"); //$NON-NLS-1$
            worker.start();
        }
    }

    /**
     * Remove animation to animator. If any animation require UI thread, this
     * method should not be called from UI thread
     * 
     * @param animation
     */
    synchronized public void removeAnimation(FigureAnimation animation) {
        animations.remove(animation);
    }

    /**
     * This method is not part of the API, should not be called by clients
     */
    public void run() {
        Object obj = new Object();
        boolean stop = !executeStep();
        while (!stop)
            synchronized (obj) {
                try {
                    long m = System.currentTimeMillis();
                    stop = !executeStep();
                    m = System.currentTimeMillis() - m;
                    if (m < STEP_WAIT_TIME)
                        obj.wait(STEP_WAIT_TIME - m);
                    else
                        obj.wait(1);
                } catch (InterruptedException e) {
                }
            }
        ;
    }

    /**
     * Execute one step on all animations;
     * 
     * @return false if there is no animations to process and working thread
     *         could be stopped.
     */
    synchronized private boolean executeStep() {
        if (animations.isEmpty()) {
            worker = null;
            return false;
        }
        List toRemove = new ArrayList();
        for (Iterator iter = animations.iterator(); iter.hasNext();) {
            FigureAnimation a = (FigureAnimation) iter.next();
            if (!a.step()) {
                toRemove.add(a);
            }
        }
        for (Iterator iter = toRemove.iterator(); iter.hasNext();) {
            animations.remove(iter.next());
        }
        return true;
    }
}
