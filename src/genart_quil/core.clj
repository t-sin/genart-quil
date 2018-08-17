(ns genart-quil.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 3)
  ; Set color mode to HSB (HSV) instead of default RGB.
  (q/color-mode :hsb)
  (q/background 150 40 40)
  (q/fill 0 0 0 0)
  (q/stroke 160 30 255 100)
  (doseq [x (range 0 500)]
    (let [th (/ x 10)
          th2 (/ x 3.15)
          r (* 200 (q/sin th2))]
      (q/ellipse x (+ 250 (* 100 (q/sin th))) r r))))

(defn -main [& args]
  (q/defsketch genart-quil
    :title "You spin my circle right round"
    :size [500 500]
    ; setup function called only once, during sketch initialization.
    :setup setup
    ; update-state is called on each iteration before draw-state.
;    :update update-state
;    :draw draw-state
    :features [:keep-on-top]
    ; This sketch uses functional-mode middleware.
    ; Check quil wiki for more info about middlewares and particularly
    ; fun-mode.
    :middleware [m/fun-mode]))
