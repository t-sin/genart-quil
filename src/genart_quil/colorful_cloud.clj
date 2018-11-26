(ns genart-quil.colorful-cloud
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def +size+ 500)

(defn make-noise-plane [x y w h]
  (for [py (range y (+ y h) (/ h +size+))]
    (for [px (range x (+ x w) (/ w +size+))]
      (q/noise px py))))

(defn make-plate [init diff max ww nsize c]
  {:n init :init init :diff diff :max max :ww ww :c c
   :nplane (make-noise-plane (q/random 2) (q/random 2) nsize nsize)})

(defn update-plate [p]
  {:n (let [n (:n p)]
        (if (>= n (:max p)) (:init p) (+ n (:diff p))))
   :init (:init p) :diff (:diff p) :max (:max p)
   :ww (:ww p) :c (:c p) :nplane (:nplane p)})

;; this is super slow function
(defn draw-plate [p]
  (doseq [y (range +size+)]
    (doseq [x (range +size+)]
      (let [n (:n p)
            v (nth (nth (:nplane p) y) x)]
        (when (and (<= n v) (< v (+ n (:ww p))))
          (apply q/stroke (:c p))
          (q/point x y))))))

(defn draw-plates [ps]
  (doseq [y (range +size+)]
    (doseq [x (range +size+)]
      (doseq [p ps]
        (let [n (:n p)
              v (nth (nth (:nplane p) y) x)]
          (when (and (<= n v) (< v (+ n (:ww p))))
            (apply q/stroke (:c p))
            (q/point x y)))))))

(defn setup-fn []
  (q/frame-rate 30)
  (q/color-mode :hsb)
  (q/background 0)
  (q/stroke-weight 1)
  (q/blend-mode :blend)
  [(make-plate -0.05 0.05 1 0.1 7.4
               [145 200 240 155])
   (make-plate 0 0.035 0.6 0.16 5
               [240 100 200 100])
   (make-plate -0.12 0.032 0.0 0.1 2
               [50 100 200 100])
   (make-plate -0.1 0.038 0.8 0.1 3.2
               [30 100 200 100])
   (make-plate -0.091 0.02 0.8 0.27 8
               [80 230 244 200])
   (make-plate -0.07 0.0735 0.9 0.34 3
               [200 80 255 40])
   (make-plate -0.5 0.01 1.2 0.45 4.32
               [180 140 120 100])])

(defn update-fn [state]
  (map update-plate state))

(defn draw-fn [state]
  (q/background 0)
  (draw-plates state)
  (q/save-frame "ccloud-####.png"))

(q/defsketch quil-test
  :title "Colorful Cloud"
  :size [+size+ +size+]
  :setup setup-fn
  :update update-fn
  :draw draw-fn
  :features [:keep-on-top]
  :middleware [m/fun-mode])
