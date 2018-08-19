(ns genart-quil.shift
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn draw-circles [x y]
  (q/fill 0 0 0 0)
  (q/stroke-weight 5)
  (q/stroke 0 200 200 150)
  (doseq [n (range 1 50)]
    (let [r (* 25 n)]
      (q/ellipse x y r r))))

(defn setup []
  (q/frame-rate 10)
  (q/color-mode :hsb)
  (q/background 0 0 0)
  (q/stroke-weight 1)
  (q/fill 0 0 0 0)
  (q/stroke 130 200 200 30)
  (doseq [n (range 0 200)]
    (let [a (* 360 (rand) (rand))
          x (+ 250 (* 10 (rand) (rand) (rand)))
          y (+ 250 (* 10 (rand) (rand) (rand)))
          rx (+ 370 (* 120 (rand) (rand) (rand)))
          ry (+ 370 (* 134 (rand) (rand) (rand)))]
      (q/rotate (q/radians a) 250 250 0)
      (q/ellipse x y rx ry))))

(defn -main [& args]
  (q/defsketch genart-quil
    :title "shift"
    :size [500 500]
    :setup setup
    :features [:keep-on-top]
    :middleware [m/fun-mode]))
