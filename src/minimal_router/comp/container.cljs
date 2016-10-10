
(ns minimal-router.comp.container
  (:require [respo-ui.style :as ui]
            [respo.alias :refer [create-comp div]]
            [respo.comp.text :refer [comp-text]]
            [minimal-router.comp.sidebar :refer [comp-sidebar]]))

; container renderer
(defn render-container [store]
  (fn [state mutate!]
    (div {:style ui/global}
      (comp-sidebar)
      ; insert text
      (comp-text store {:font-family "Menlo, Consolas, monospace"}))))

; container component
(def comp-container (create-comp :container render-container))
