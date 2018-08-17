(ns genart-quil.retro-stars
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :hsb)
  (q/fill 160 140 30 255)
  (q/stroke-weight 0)
  (q/rect 0 0 500 500)
  (let [randpos #(int (Math/floor (rand 500)))]
    (take 40 (repeatedly #(hash-map :x (randpos)
                                    :y (randpos)
                                    :h (int (rand 256)))))))

(defn update-state [state]
  (map (fn [p] {:x (inc (p :x)) :y (p :y) :h (p :h)})
       state))

(defn draw-state [state]
  (q/fill 160 140 30 10)
  (q/stroke-weight 0)
  (q/rect 0 0 500 500)
  (q/stroke-weight 1)
  (doseq [p state]
    (q/stroke (p :h) 150 255)
    (q/point (p :x) (p :y))))

(defn -main [& args]
  (q/defsketch genart-quil
    :title "draw stars in old shooter games"
    :size [500 500]
    :setup setup
    :update update-state
    :draw draw-state
    :features [:keep-on-top]
    :middleware [m/fun-mode]))
