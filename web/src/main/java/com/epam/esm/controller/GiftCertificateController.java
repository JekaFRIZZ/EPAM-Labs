package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.entity.ErrorData;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.OrderSort;
import com.epam.esm.exception.ResourceExistenceException;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/gifts")
public class GiftCertificateController {

    private static final String PRODUCES = "application/json";

    private final GiftCertificateService giftCertificateService;
    private final MessageSource messageSource;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, MessageSource messageSource) {
        this.giftCertificateService = giftCertificateService;
        this.messageSource = messageSource;
    }

    /**
     * Returns all {@link GiftCertificate} from database
     *
     * @return {@link ResponseEntity} with a {@link HttpStatus} and all {@link GiftCertificate}.
     */
    @GetMapping(produces = PRODUCES)
    public ResponseEntity<?> getAll(@RequestParam Map<String, String> requestParam) {
        String fieldName = null;
        OrderSort isASC = OrderSort.ASC;
        List<GiftCertificate> giftCertificates = null;

        if (requestParam.get("fieldName") != null || requestParam.get("FIELDNAME") != null) {
            if (requestParam.get("sort") != null || requestParam.get("SORT") != null) {
                try {
                    fieldName = requestParam.get("fieldName") != null ?
                            requestParam.get("fieldName") : requestParam.get("FIELDNAME");

                    isASC = requestParam.get("sort") != null ?
                            OrderSort.valueOf(requestParam.get("sort")) : OrderSort.valueOf(requestParam.get("SORT"));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Incorrect parameter");
                }
            }
        } else {
            giftCertificates = giftCertificateService.getAll();
            return new ResponseEntity<>(giftCertificates, HttpStatus.OK);
        }

        giftCertificates = giftCertificateService.sortByOrder(fieldName, isASC);
        return new ResponseEntity<>(giftCertificates, HttpStatus.OK);
    }

    /**
     * Returns {@link GiftCertificate} from a database by id or throws {@link ResourceExistenceException} if {@link GiftCertificate} not exist
     *
     * @param id - id's {@link GiftCertificate}
     * @return {@link ResponseEntity} with a {@link HttpStatus} and a {@link GiftCertificate} object or a {@link ErrorData} object.
     */
    @GetMapping(value = "/{id}", produces = PRODUCES)
    public ResponseEntity<?> getGiftById(@PathVariable("id") Integer id) {
        GiftCertificate giftCertificate = giftCertificateService.getById(id);
        return new ResponseEntity<>(giftCertificate, HttpStatus.OK);
    }

    /**
     * Updates {@link GiftCertificate} by id's {@link GiftCertificate}
     *
     * @param id              - id's {@link GiftCertificate} that should be updated
     * @param giftCertificate - object which will be updated
     * @return {@link ResponseEntity} with {@link HttpStatus} alone or additionally with {@link ErrorData} object.
     */
    @PatchMapping(value = "/{id}", produces = PRODUCES)
    public ResponseEntity<?> updateById(@PathVariable Integer id, @RequestBody GiftCertificate giftCertificate) {
        giftCertificateService.update(id, giftCertificate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Creates {@link GiftCertificate}
     *
     * @param giftCertificateDTO - object that will be converted to {@link GiftCertificate} and save to database
     * @return {@link ResponseEntity} with {@link HttpStatus} alone or additionally with {@link ErrorData} object.
     */
    @PostMapping(produces = PRODUCES)
    public ResponseEntity<?> create(@RequestBody GiftCertificateDTO giftCertificateDTO) {
        giftCertificateService.create(giftCertificateDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    /**
     * Deletes {@link GiftCertificate} by id if exist
     *
     * @param id {@link GiftCertificate} id which will be deleted
     * @return {@link ResponseEntity} with {@link HttpStatus} alone or additionally with {@link ErrorData} object.
     */
    @DeleteMapping(value = "/{id}", produces = PRODUCES)
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        giftCertificateService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
