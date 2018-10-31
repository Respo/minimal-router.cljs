
(ns app.main
  (:require [respo.core :refer [render! clear-cache!]]
            [clojure.string :as string]
            [respo-router.core :refer [render-url!]]
            [app.comp.container :refer [comp-container]]
            [respo-router.parser :refer [parse-address]]
            [respo-router.listener :refer [listen!]]))

; using hash for convenience
(def router-mode :hash)

; rules of the router
(def dict
  {"home" []
   "about" []
   "post" ["post"]})

; parser router and save it in router
(defonce *store
  (atom {:router (parse-address
                    (string/replace (.-hash js/location) #"^#" "")
                    dict)
         :states {}}))

(defn updater [store op op-data]
  (case op
    ; accept router in HashMap and save into store
    :router/route (assoc store :router op-data)
    ; accept router in string and parse before saving into store
    :router/nav (assoc store :router (parse-address op-data dict))
    store))

(defn dispatch! [op op-data]
  (reset! *store (updater @*store op op-data)))

(def mount-target (.querySelector js/document "#app"))

(defn render-app! []
  (println "Mounting" mount-target)
  (render! mount-target (comp-container @*store) dispatch!))

; render router with router data in store
(defn render-router! []
  (render-url! (:router @*store) dict router-mode))

(defn main! []
  (render-app!)
  (add-watch *store :changes render-app!)
  ; render address in the first screen
  (render-router!)
  ; listen to url changes and call dispatch!
  (listen! dict dispatch! router-mode)
  ; watch updates and render address
  (add-watch *store :router-changes render-router!)
  (println "app started!"))

(defn reload! []
  (clear-cache!)
  (render-app!)
  (println "code updated."))
