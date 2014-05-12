(ns testme-now.handler
  (:use compojure.core cornet.core cornet.paths cornet.processors.lesscss cornet.utils cornet.wrappers cornet.route cornet.loader clojure.tools.trace)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(trace-vars cornet.loader/resource-loader)
(trace-vars cornet.loader/file-loader)
(trace-vars cornet.paths/relative-filename)
(trace-ns 'cornet.core)
(trace-ns 'cornet.paths)
(trace-ns 'cornet.loader)
(trace-ns 'cornet.utils)
(trace-ns 'cornet.wrappers)
(trace-ns 'cornet.processors.lesscss)


(defn simple-logging-middleware [app]
  (fn [req]
    (println req)
    (app req)))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (wrap-url-response
         (some-fn
             (compiled-assets-loader "precompiled"
                                     :lesscss-list ["less/test.less"]
                                     :mode :dev ; or :dev for minification
                                     )
             (static-assets-loader "public" 
                                     :mode :dev))
  (route/not-found "Not Found")))

(def app
  ( simple-logging-middleware
  (handler/site app-routes)))


