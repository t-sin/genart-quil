(ns genart-quil.flow-brash1
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :hsb)
  (q/background 130 60 40)
  (q/blend-mode :subtract)
  (q/stroke-weight 1)
  {:img (q/load-image "icon.png")
   :ps (take 30 (repeatedly #(let [x (rand 500)
                                   y (rand 500)]
                               (hash-map :x x :y y
                                         :dx (+ 0.05201 (rand 0.022))
                                         :dy (+ 0.0551 (rand 0.0367))
                                         :xtrail (take 60 (repeat x))
                                         :ytrail (take 60 (repeat y))))))})

(defn update-state [state]
  {:img (state :img)
   :ps (for [p (state :ps)]
         (let [x (+ (p :x) (* (p :dx) (- (q/mouse-x) (p :x))))
               y (+ (p :y) (* (p :dy) (- (q/mouse-y) (p :y))))]
           (hash-map :x x :y y :dx (p :dx) :dy (p :dy)
                     :xtrail (take 60 (cons x (p :xtrail)))
                     :ytrail (take 60 (cons y (p :ytrail))))))})

(defn draw-state [state]
  (q/fill 130 60 40 0)
  (q/stroke 0 0 0 0)
  (q/rect 0 0 500 500)
  (let [img (state :img)]
    (doseq [p (state :ps)]
      (let [c (q/get-pixel img (p :x) (p :y))]
        (q/stroke (q/hue c) (q/saturation c) (q/brightness c) 10)
        (q/line (p :x) (p :y)
                (first (reverse (p :xtrail))) (first (reverse (p :ytrail))))))))

(defn -main [& args]
  (q/defsketch genart-quil
    :title "flow brash v1"
    :size [500 500]
    :setup setup
    :update update-state
    :draw draw-state
    :features [:keep-on-top]
    :middleware [m/fun-mode]))
