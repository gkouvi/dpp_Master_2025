const CACHE_NAME = "dpp-mobile-cache-v1";

const FILES_TO_CACHE = [
    "/mobile/index.html",
    "/mobile/device.html",
    "/mobile/maintenance.html",
    "/mobile/style.css",
    "/mobile/device.js",
    "/mobile/maintenance.js",
    "/mobile/qrcode-scanner.js",
    "/mobile/manifest.json"
];
function getToken() {
    const t = localStorage.getItem("jwt");
    if (!t) {
        window.location.href = "login.html";
        return null;
    }
    return t;
}

// Install (cache files)
self.addEventListener("install", event => {
    console.log("Service Worker: Installed");
    event.waitUntil(
        caches.open(CACHE_NAME).then(cache => {
            return cache.addAll(FILES_TO_CACHE);
        })
    );
    self.skipWaiting();
});

// Activate
self.addEventListener("activate", event => {
    console.log("Service Worker: Activated");
    event.waitUntil(
        caches.keys().then(keys => {
            return Promise.all(
                keys.map(key => {
                    if (key !== CACHE_NAME) {
                        return caches.delete(key);
                    }
                })
            );
        })
    );
    self.clients.claim();
});

// Fetch (serve from cache first)
self.addEventListener("fetch", event => {
    event.respondWith(
        caches.match(event.request).then(cached => {
            return cached || fetch(event.request);
        })
    );
});
