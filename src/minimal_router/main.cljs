
(ns minimal-router.main
  (:require [respo.core :refer [render! clear-cache!]]
            [clojure.string :as string]
            [respo-router.core :refer [render-url!]]
            [minimal-router.comp.container :refer [comp-container]]
            [respo-router.util.listener :refer [listen! parse-address]]))

; using hash for convenience
(def router-mode :hash)

; rules of the router
(def dict
  {"home" []
   "about" []
   "post" ["post"]})

; states tree, make sure to use {}
(defonce states-ref (atom {}))

; parser router and save it in router
(defonce store-ref
  (atom {:router (parse-address
                    (string/replace (.-hash js/location) #"^#" "")
                    dict)}))

(defn updater [store op op-data]
  (case op
    ; accept router in HashMap and save into store
    :router/route (assoc store :router op-data)
    ; accept router in string and parse before saving into store
    :router/nav (assoc store :router (parse-address op-data dict))
    store))

(defn dispatch! [op op-data]
  (reset! store-ref (updater @store-ref op op-data)))

(defn render-app! []
  (let [target (.querySelector js/document "#app")]
    (render!
      (comp-container @store-ref)
      target dispatch! states-ref)))

; render router with router data in store
(defn render-router! []
  (render-url! (:router @store-ref) dict router-mode))

(defn -main! []
  (enable-console-print!)
  (render-app!)
  (add-watch store-ref :changes render-app!)
  (add-watch states-ref :changes render-app!)
  ; render address in the first screen
  (render-router!)
  ; listen to url changes and call dispatch!
  (listen! dict dispatch! router-mode)
  ; watch updates and render address
  (add-watch store-ref :router-changes render-router!)
  (println "app started!"))

(set! (.-onload js/window) -main!)

(defn on-jsload! []
  (clear-cache!)
  (render-app!)
  (println "code updated."))
