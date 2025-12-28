const codeReader = new ZXingBrowser.BrowserMultiFormatReader();
const startBtn = document.getElementById("startBtn");
function getToken() {
    const t = localStorage.getItem("jwt");
    if (!t) {
        window.location.href = "login.html";
        return null;
    }
    return t;
}
function logout() {
    localStorage.removeItem("jwt");
    window.location.href = "login.html";
}


startBtn.addEventListener("click", async () => {

    const videoElem = document.createElement("video");
    videoElem.style.width = "100%";

    document.getElementById("scanner").appendChild(videoElem);

    try {
        const devices = await ZXingBrowser.BrowserMultiFormatReader.listVideoInputDevices();

        // open rear camera (back camera)
        const backCamera = devices.find(d => d.label.toLowerCase().includes("back")) || devices[0];

        codeReader.decodeFromVideoDevice(backCamera.deviceId, videoElem, (result, err) => {
            if (result) {
                const scanned = result.text;
                console.log("QR:", scanned);

                // ------- Handle scanned QR -------
                handleScan(scanned);
            }
        });

    } catch (e) {
        alert("Camera access error: " + e);
    }
});

function handleScan(text) {

    // For devices
    if (text.startsWith("DPP://device/")) {
        const id = text.replace("DPP://device/", "");
        window.location.href = "device.html?id=" + id;
        return;
    }

    // For buildings
    if (text.startsWith("DPP://building/")) {
        const id = text.replace("DPP://building/", "");
        window.location.href = "building.html?id=" + id;
        return;
    }

    // For sites
    if (text.startsWith("DPP://site/")) {
        const id = text.replace("DPP://site/", "");
        window.location.href = "site.html?id=" + id;
        return;
    }

    alert("Μη έγκυρο QR");
}
