(ns genart-quil.paren01
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(let [width 500
      height 500
      num 30
      rings 5]
  (q/defsketch paren01
    :title "paren-holic"
    :size [width height]
    :setup (fn []
             (q/frame-rate 3)
             (q/color-mode :hsb)
             (q/background 20 10 255)
             (q/translate (/ width 2) (/ height 2))
             (q/text-size 50)
             (dotimes [r rings]
               (q/push-matrix)
               (let [n (+ num (* r 5))
                     paren (if (even? r) "(" ")")]
                 (dotimes [i n]
                   (q/fill (+ 100 (* 100 (q/sin (* 4 (/ i q/PI 2))))))
                   (q/rotate (q/radians (/ 360 n)))
                   (q/text paren 0 (+ 40 (* 70 r)))))
               (q/pop-matrix)))
    :features [:keep-on-top]
    :middleware [m/fun-mode]))
