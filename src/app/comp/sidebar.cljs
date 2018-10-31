
(ns app.comp.sidebar
  (:require-macros [respo.core :refer [defcomp div span <>]])
  (:require [respo-ui.core :as ui]
            [respo.core :refer [create-comp]]))

(defn on-route [path]
  (fn [e dispatch!]
    (dispatch! :router/nav path)))

(defcomp comp-sidebar []
  (div {}
    (div {}
      (div {:style ui/button
            :on-click (on-route "/")}
        (<> "/")))
    (div {}
      (div {:style ui/button
            :on-click (on-route "/about")}
        (<> "/about")))
    (div {}
      (div {:style ui/button
            :on-click (on-route "/post/a")}
        (<> "/post/a")))
    (div {}
      (div {:style ui/button
            :on-click (on-route "/post/b")}
        (<> "/post/b")))))
