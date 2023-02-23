package org.keycloak.mapper.eauth;

/**
 * User: ggeorgiev
 * Date: 22.02.2023
 * Time: 15:28
 */
public class EauthUserAttributeMapper extends EauthBaseAttributeMapper {
    public EauthUserAttributeMapper() {
        addAllAttributes();
    }

    @Override
    public String getId() {
        return "eauth-user-attribute-mapper";
    }
}
