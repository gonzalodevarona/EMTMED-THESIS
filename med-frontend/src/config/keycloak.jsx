import Keycloak from "keycloak-js"

const keycloak = new Keycloak({
    url: "http://keycloak:9080",
    realm: "jhipster",
    clientId: "web_app"
})

export default keycloak