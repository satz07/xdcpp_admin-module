package com.adminremit.component;

import com.adminremit.auth.models.*;
import com.adminremit.auth.repository.UserRepository;
import com.adminremit.auth.service.*;
import com.adminremit.masters.enums.RoleKeys;
import com.adminremit.masters.models.LocationMaster;
import com.adminremit.masters.service.LocationMasterService;
import liquibase.pro.packaged.O;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PostConstructConfig {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleMasterService roleMasterService;
    
    @Autowired
    private UserRoleMappingService userRoleMappingService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private GroupMasterService groupMasterService;

    @Autowired
    private GroupRoleMappingService groupRoleMappingService;

    @Autowired
    private LocationMasterService locationMasterService;

    @Autowired
    private OwnerDesignationService ownerDesignationService;

    @Value("${admin.andremit.firstname}")
    private String firstName;

    @Value("${admin.andremit.lastname}")
    private String lastName;

    @Value("${admin.andremit.username}")
    private String userName;

    @Value("${admin.andremit.password}")
    private String password;

    @Value("${admin.andremit.default.role}")
    private String role;

    @Value("${admin.partner.name}")
    private String partnerName;

    @Value("${admin.product.name}")
    private String productName;

    @Value("${admin.group.name}")
    private String groupName;

    @PostConstruct
    public void initIt() throws Exception {
        User user1 = userService.findByEmail(userName);
        if(user1 == null) {

            LocationMaster locationMaster = new LocationMaster();
            locationMaster.setLocationName("India");
            locationMasterService.save(locationMaster);

            OwnerDesignation ownerDesignation = new OwnerDesignation();
            ownerDesignation.setPublish(true);
            ownerDesignation.setDesignation("Super Admin");
            ownerDesignationService.save(ownerDesignation);

            Product product = new Product();
            product.setProductCode(productService.getProductId().toString());
            product.setProductName(productName);
            product.setPublish(true);
            product.setLocationMaster(locationMaster);
            product.setOwnerDesignation(ownerDesignation);
            product.setOfficialEmail("superadmin@andremitt.com");
            product.setCountryCode("91");
            product.setPhoneNumber("1111111111");
            product.setOwnerName("Super Admin");
            product.setProductDescription("Andremitt product");
            productService.save(product);

            Partner partner = new Partner();
            partner.setPartnerName(partnerName);
            partner.setPartnerId(productService.getProductId().toString());
            partner.setProduct(product);
            partner.setDescription("Description");
            partner.setLocationMaster(locationMaster);
            partner.setOfficialEmail("superadmin@andremitt.com");
            partner.setPublish(true);
            partner.setAddress("Address");
            partner.setPhoneNumber("11111111");
            partner.setDialingCode("91");
            partner.setOwnerDesignation(ownerDesignation);
            partner.setOwnerName("Super Admin");
            partnerService.save(partner);

            User user = new User();
            user.setEmail(userName);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(userService.passwordEncryption(password));
            user.setIsPublish(true);
            user.setPartner(partner);
            userService.save(user);

            GroupMaster groupMaster = new GroupMaster();
            groupMaster.setGroupName(groupName);
            groupMaster.setGroupDescription(groupName);
            groupMaster.setPublish(true);
            groupMaster.setLocationMaster(locationMaster);
            groupMasterService.save(groupMaster);

            RoleMaster roleMaster = new RoleMaster();
            roleMaster.setRoleName(role);
            roleMaster.setPublish(true);
            roleMasterService.save(roleMaster);

            GroupRoleMapping groupRoleMapping = new GroupRoleMapping();
            groupRoleMapping.setRoleMaster(roleMaster);
            groupRoleMapping.setGroupMaster(groupMaster);
            groupRoleMappingService.save(groupRoleMapping);

            UserRoleMapping userRoleMapping = new UserRoleMapping();
            userRoleMapping.setRole(roleMaster);
            userRoleMapping.setUserRole(user);
            userRoleMapping.setGroupId(groupMaster.getId());
            userRoleMappingService.save(userRoleMapping);
        } else {
            // Update password if user already exists
            user1.setPassword(userService.passwordEncryption(password));
            userRepository.save(user1);
        }
    }

}
