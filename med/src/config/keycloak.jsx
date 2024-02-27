import Keycloak from "keycloak-js"

const keycloak = new Keycloak({
    url: "http://localhost:9080",
    realm: "emt",
    clientId: "med_web_app"
})

export default keycloak