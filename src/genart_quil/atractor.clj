(ns genart-quil.atractor
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def width 800)
(def height 600)

(defn make-circle [x y r]
  {:x x :y y :r r})

(defn make-circles []
  (for [x (range -20 (+ width 20) 15)
        y (range -20 (+ height 20) 15)]
    (make-circle x y 15)))

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :hsb)
  (q/background 0)
  (q/fill 0 0 0 0)
  (q/blend-mode :blend)
  {:lines (let [xgap 5
                ygap 13]
            (for [x (range -10 (+ width 10) xgap)
                  y (range -10 (+ height 10) ygap)]
              {:x1 x :y1 y
               :x2 (+ xgap x -1) :y2 y}))
   :atractors (for [_ (range 10)]
                (let [x (q/random width)
                      y (q/random height)
                      r (+ 50 (q/random 200.0))
                      s (- (q/random 10.0) 5.0)
                      swirl (if (zero? (q/round (q/random 1))) true false)]
                  {:x x :y y :r r :s s :swirl swirl}))})

(defn atract [p a]
  (let [x (:x p)
        y (:y p)
        ax (:x a)
        ay (:y a)
        d (q/dist x y ax ay)]
    (if (and (> d 0) (< d (:r a)))
      (let [s (/ d (:r a))
            f (- (/ 1 (q/pow s 0.5)) 1)
            $f (* (:s a) (/ f (:r a)))]
        (if (:swirl a)
          {:x (+ x (* $f (- ay y)))
           :y (+ y (* -1 $f (- ax x)))}
          {:x (+ x (* $f (- ax x)))
           :y (+ y (* $f (- ay y)))}))
      p)))

(defn doatract [ls a]
  (for [l ls]
    (let [p1 (atract {:x (:x1 l) :y (:y1 l)} a)
          p2 (atract {:x (:x2 l) :y (:y2 l)} a)]
      {:x1 (:x p1) :y1 (:y p1)
       :x2 (:x p2) :y2 (:y p2)})))

(defn update [state]
  {:lines (reduce doatract (:lines state) (:atractors state))
   :atractors (:atractors state)})

(defn draw [state]
  (q/fill 0 0 0 12)
  (q/rect 0 0 width height)
;;  (q/background 0)
  (q/stroke 140 255 240 170)
  (q/stroke-weight 1)
  (doseq [l (:lines state)]
    (q/line (:x1 l) (:y1 l)
            (:x2 l) (:y2 l))))

(q/defsketch quil-test
  :title "simple atractors"
  :size [width height]
  :setup setup
  :update update
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])
