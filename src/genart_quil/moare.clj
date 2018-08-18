(ns genart-quil.moare
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
  (q/frame-rate 60)
  (q/color-mode :hsb)
  (q/fill 10 20 230 255)
  (q/stroke-weight 0)
  (q/rect 0 0 500 500)
  {:deg 0
   :uki 0})

(defn update-state [state]
  {:deg (inc (state :deg))
   :uki (+ (state :uki) 0.1)})

(defn draw-state [state]
  (q/background 10 20 230 255)
  (q/fill 160 140 30 10)
  (let [r 100]
    (draw-circles (+ 250 (* (q/cos (state :uki)) r (q/cos (q/radians (state :deg)))))
                  (+ 250 (* (q/sin (state :uki)) r (q/sin (q/radians (state :deg))))))
    (draw-circles (+ 250 (* (q/cos (state :uki)) r (q/cos (q/radians (+ 120 (state :deg))))))
                  (+ 250 (* (q/sin (state :uki)) r (q/sin (q/radians (+ 120 (state :deg)))))))
    (draw-circles (+ 250 (* (q/cos (state :uki)) r (q/cos (q/radians (+ 240 (state :deg))))))
                  (+ 250 (* (q/sin (state :uki)) r (q/sin (q/radians (+ 240 (state :deg))))))))
  (q/save-frame "moare-####.png"))
  

(defn -main [& args]
  (q/defsketch genart-quil
    :title "You Made Me Realise"
    :size [500 500]
    :setup setup
    :update update-state
    :draw draw-state
    :features [:keep-on-top]
    :middleware [m/fun-mode]))
