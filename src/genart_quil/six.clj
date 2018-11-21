(ns genart-quil.six
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  (q/color-mode :hsb)
  (q/background 150 40 40)
  (q/fill 0 0 0 0)
  (q/translate 350 350)
  (q/blend-mode :blend)
  (let [split-n 10
        times-n 8]
    (doseq [i (range 0 times-n)]
      (doseq [d (range 0 split-n)]
        (q/push-matrix)
        (q/rotate (q/radians (+ (* i 20) (* d (/ 360 split-n)))))
        (doseq [n (range 0 10)]
          (q/stroke-weight 1)
          (q/stroke (+ 40 (* i 40)) (+ 30 (* 30 n)) 255 120)
          (q/arc 0 0 (+ (* n 30) (* 1.5 i 50)) (+ (* n 30) (* 1.2 i 200))
                 (q/radians (- times-n i)) (q/radians (/ 360 split-n)))
          (q/stroke-weight 13)
          (q/stroke (+ 40 (* i 40)) (* 30 n) 255 5)
          (q/arc 0 0 (+ (* n 30) (* 1.5 i 50)) (+ (* n 30) (* 1.2 i 200))
                 (q/radians (- times-n i)) (q/radians (/ 360 split-n))))
        (q/pop-matrix))))
  (q/save-frame "six.png"))

(q/defsketch genart-quil
  :title "You spin my circle right round"
  :size [700 700]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update-state is called on each iteration before draw-state.
;    :update update-state
;    :draw draw-state
  :features [:keep-on-top]
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])
