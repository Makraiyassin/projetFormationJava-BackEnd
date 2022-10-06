package be.digitalcity.projetspringrest.utils;


//@Service
//public class DataInit implements InitializingBean {
//
//    private final OmnithequeService omnithequeService;
//    private final UsersDetailsServiceImpl usersService;
//    private final ProductService productService;
//    private final AddressService addressService;
//
//    public DataInit(OmnithequeService omnithequeService, UsersDetailsServiceImpl usersService, ProductService productService, AddressService addressService) {
//        this.omnithequeService = omnithequeService;
//        this.usersService = usersService;
//        this.productService = productService;
//        this.addressService = addressService;
//    }
//
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        List<AddressForm> addressList = Arrays.asList(
//            new AddressForm("digitalcity", 1, 1000, "Bruxelles", "Belgique"),
//            new AddressForm("ch de ninove", 1, 1070, "Bruxelles", "Belgique"),
//            new AddressForm("rue haute", 1, 1060, "Bruxelles", "Belgique"),
//            new AddressForm("boulevard leopold", 1, 1210, "Bruxelles", "Belgique")
//        );
//        List<ProductForm> productList = Arrays.asList(
//            new ProductForm("One piece Red", Category.MOVIE),
//            new ProductForm("Harry Potter", Category.BOOK),
//            new ProductForm("Fifa 2022", Category.VIDEOGAME),
//            new ProductForm("Monopoly", Category.BOARDGAME)
//        );
//        List<OmnithequeForm> omnithequeList = Arrays.asList(
//            new OmnithequeForm("ludotheque speculoos", "022175698", "hello@hola.com", addressList.get(1)),
//            new OmnithequeForm("espace video", "022175698", "hello@hola.com", addressList.get(2)),
//            new OmnithequeForm("mediacity", "022175698", "hello@hola.com", addressList.get(3)),
//            new OmnithequeForm("le libraire", "022175698", "hello@hola.com", addressList.get(1))
//        );
//        List<UsersForm> usersList = Arrays.asList(
//                new UsersForm ("Hello", "Hola", LocalDate.of(1992,1,1),addressList.get(1),"900-633-9958","string@string.com", "string"),
//                new UsersForm ("Hello", "Hola", LocalDate.of(1992,1,1),addressList.get(2),"900-633-9958","string2@string.com", "string"),
//                new UsersForm ("Hello", "Hola", LocalDate.of(1992,1,1),addressList.get(3),"900-633-9958","string3@string.com", "string"),
//                new UsersForm ("Hello", "Hola", LocalDate.of(1992,1,1),addressList.get(2),"900-633-9958","string4@string.com", "string"),
//                new UsersForm ("Hello", "Hola", LocalDate.of(1992,1,1),addressList.get(0),"900-633-9958","string5@string.com", "string")
//        );
//
//        addressList.forEach(addressService::create);
//        productList.forEach(productService::create);
//        omnithequeList.forEach(omnithequeService::create);
//        usersList.forEach(usersService::create);
//
//    }
//
//}

import be.digitalcity.projetspringrest.models.entities.Roles;
import be.digitalcity.projetspringrest.repositories.RolesRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class DataInit implements InitializingBean {
    private final RolesRepository rolesRepository;

    public DataInit(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if(!rolesRepository.existsByName("USER")) rolesRepository.save(new Roles("USER"));
        if(!rolesRepository.existsByName("PRO")) rolesRepository.save(new Roles("PRO"));
        if(!rolesRepository.existsByName("ADMIN")) rolesRepository.save(new Roles("ADMIN"));
    }
}

