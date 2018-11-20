(ns genart-quil.soundness
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn make-circle [x y r]
  {:x x :y y :r r})

(defn tessellate-line [circle deg margin]
  (lazy-seq
   (cons circle
         (tessellate-line (make-circle (+ (:x circle) (- margin) (* (:r circle) (q/cos (q/radians deg))))
                                       (+ (:y circle) (- margin) (* (:r circle) (q/sin (q/radians deg))))
                                       (:r circle))
                          deg margin))))

(defn tessellate-vertical [clis deg margin]
  (lazy-seq
   (cons clis
         (tessellate-line (make-circle (:x (nth clis 0))
                                       (+ (:y (nth clis 0)) (:r (nth clis 0)))
                                       (:r (nth clis 0)))
                          deg
                          margin))))

(defn draw-circles [clis]
  (loop [clis clis]
    (let [c (first clis)]
      (q/ellipse (:x c) (:y c) (:r c) (:r c))
      (recur (next clis)))))

(defn draw-tessellate [clislis]
  (loop [clislis clislis]
    (let [clis (first clislis)]
      (draw-circles clis)
      (recur (next clislis)))))

(defn xcont? [circle]
  (< (- (:x circle) (:r circle)) 700))

(defn ycont? [clis]
  (let [c (nth clis 0)]
    (< (- (:y c) (:r c)) 700)))

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :hsb)
  (q/background 0)
  (q/fill 0 0 0 0)
  (q/blend-mode :blend)
  (q/stroke 140 255 240 100)
  (let [initc (make-circle -10 0 100)
        deg 0
        margin 0]
    (draw-tessellate (take-while ycont?
                                 (tessellate-vertical
                                  (take-while xcont? (tessellate-line initc deg margin))
                                  deg margin)))))

(q/defsketch quil-test
  :title "Tessellate something..."
  :size [700 700]
  :setup setup
  :features [:keep-on-top]
  :middleware [m/fun-mode])
