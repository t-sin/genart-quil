(ns genart-quil.gen
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  (q/frame-rate 20)
  (q/color-mode :hsb)
  (q/rect 0 0 500 500)
  (q/fill 160 140 30 10)
  (q/fill 0 0 0 0)
  (q/stroke-weight 5)
  (q/stroke 150 200 200 150)
  {:x 0 :th 0})

(defn update-state [state]
  state)

(defn draw-state [state]
  (q/background 150 20 30)
  (let [n 25
        d (/ 500 n)]
    (doseq [x (range 0 (inc n))]
      (doseq [y (range 0 (inc n))]
        (q/stroke (* 100 (q/noise (/ x 3.3) (/ y 5.1) (/ (q/mouse-x) 200))) 200 200 150)
        (q/ellipse (+ (* x d) (* (q/noise (/ x 2) (/ y 3.1) (/ (q/mouse-x) 252)) 20))
                   (+ (* y d) (* (q/noise (/ x 1.34) (/ y 2.1) (/ (q/mouse-x) 262)) 20))
                   18 18)))))

;; (q/save-frame "ymmr-####.png"))

(q/defsketch genart-quil
  :title "generative design test (1)"
  :size [500 500]
  :setup setup
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode])
