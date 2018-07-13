package eu.fiestaiot.reasoner.service.web.rest;

import eu.fiestaiot.reasoner.service.security.jwt.JWTConfigurer;
import eu.fiestaiot.reasoner.service.security.jwt.TokenProvider;

import java.util.Collections;

import com.codahale.metrics.annotation.Timed;
import eu.fiestaiot.reasoner.service.service.OpenAMSecurityHelper;
import eu.fiestaiot.reasoner.service.web.rest.vm.AccountVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserJWTController {

    @Inject
    private TokenProvider tokenProvider;

    @Inject
    private AuthenticationManager authenticationManager;

    @Inject
    private OpenAMSecurityHelper openAMSecurityHelper;

    private final Logger log = LoggerFactory.getLogger(ExecutionResource.class);

//    @PostMapping("/authenticate")
//    @Timed
//    public ResponseEntity<?> authorize(@Valid @RequestBody LoginVM loginVM, HttpServletResponse response) {
//
//        UsernamePasswordAuthenticationToken authenticationToken =
//            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());
//
//        try {
//            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
//            String jwt = tokenProvider.createToken(authentication, rememberMe);
//            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
//            return ResponseEntity.ok(new JWTToken(jwt));
//        } catch (AuthenticationException exception) {
//            return new ResponseEntity<>(Collections.singletonMap("AuthenticationException",exception.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
//        }
//    }

    @GetMapping("/getCurrentAccount")
    public ResponseEntity<AccountVM> getCurrentAccount(HttpServletRequest request) {

        String token = openAMSecurityHelper.getToken(request);
        log.info("REST request with cookie token : {}", token);
        String userID = openAMSecurityHelper.getUserID(token);
        AccountVM vm = new AccountVM();
        vm.setUserID(userID);
        //return new ResponseEntity.ok(userID);
        return new ResponseEntity<>(vm, null, HttpStatus.OK);
    }
}
