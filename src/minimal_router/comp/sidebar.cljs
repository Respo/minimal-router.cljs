
(ns minimal-router.comp.sidebar
  (:require [respo-ui.style :as ui]
            [respo.alias :refer [create-comp div]]
            [respo.comp.text :refer [comp-text]]))

(defn on-route [path]
  (fn [e dispatch!]
    (dispatch! :router/nav path)))

(defn render []
  (fn [state mutate!]
    (div {}
      (div {}
        (div {:style ui/button
              :event {:click (on-route "/")}}
          (comp-text "/" nil)))
      (div {}
        (div {:style ui/button
              :event {:click (on-route "/about")}}
          (comp-text "/about" nil)))
      (div {}
        (div {:style ui/button
              :event {:click (on-route "/post/a")}}
          (comp-text "/post/a" nil)))
      (div {}
        (div {:style ui/button
              :event {:click (on-route "/post/b")}}
          (comp-text "/post/b" nil))))))

(def comp-sidebar (create-comp :sidebar render))
