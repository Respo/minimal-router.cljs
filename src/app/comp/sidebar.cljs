
(ns app.comp.sidebar
  (:require-macros [respo.macros :refer [defcomp div span <>]])
  (:require [respo-ui.style :as ui]
            [respo.core :refer [create-comp]]))

(defn on-route [path]
  (fn [e dispatch!]
    (dispatch! :router/nav path)))

(defcomp comp-sidebar []
  (div {}
    (div {}
      (div {:style ui/button
            :event {:click (on-route "/")}}
        (<> "/")))
    (div {}
      (div {:style ui/button
            :event {:click (on-route "/about")}}
        (<> "/about")))
    (div {}
      (div {:style ui/button
            :event {:click (on-route "/post/a")}}
        (<> "/post/a")))
    (div {}
      (div {:style ui/button
            :event {:click (on-route "/post/b")}}
        (<> "/post/b")))))
