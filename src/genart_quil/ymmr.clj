(ns genart-quil.ymmr
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn draw-circles [x y]
  (doseq [n (range 1 50)]
    (let [r (* 20 n)]
      (q/ellipse x y r r))))

(defn setup []
  (q/frame-rate 60)
  (q/color-mode :hsb)
  (q/fill 10 20 230 255)
  (q/stroke-weight 0)
  (q/rect 0 0 500 500)
  {:x 0 :th 0})

(defn update-state [state]
  {:x (* 170 (q/sin (state :th))) :th (+ 0.01 (state :th))})

(defn draw-state [state]
  (q/background 10 20 230 255)
  (q/fill 160 140 30 10)
  (q/fill 0 0 0 0)
  (q/stroke-weight 5)
  (q/stroke 0 200 200 150)
  (draw-circles (+ 250 (state :x)) 250)
  (draw-circles (- 250 (state :x)) 250)
  (q/save-frame "ymmr-####.png"))

(defn -main [& args]
  (q/defsketch genart-quil
    :title "You Made Me Realise"
    :size [500 500]
    :setup setup
    :update update-state
    :draw draw-state
    :features [:keep-on-top]
    :middleware [m/fun-mode]))
