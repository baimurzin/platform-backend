boolean mainMethod(com.pwrstd.platform.backend.service.groovy.GroovyApiContextService api){
    def get = api.get("arepo_name")
    if(get == null) {
        return false
    }
    return api.isRepositoryExist(get)
}