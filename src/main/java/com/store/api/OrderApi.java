package com.store.api;

import com.store.domain.Customer;
import com.store.domain.Order;
import com.store.message.ExceptionMessage;
import com.store.message.ValidatorFieldMessage;
import com.store.resource.OrderResource;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@Api(value = "order")
@RequestMapping(value = "/customers/{customerId}/orders")
@ExposesResourceFor(Order.class)
public interface OrderApi {

    @ApiOperation(value = "Add a new order", nickname = "addOrder", tags = {"order",})
    @ApiResponses(value = {
            @ApiResponse(code = 405, message = ValidatorFieldMessage.InvalidInput),
            @ApiResponse(code = 404, message = ExceptionMessage.CustomerNotFound)
    })
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<OrderResource> addOrder(@ApiParam(value = "Customer id of the order", example = "1", required = true) @Valid @PathVariable Integer customerId,
                                           @ApiParam(value = "Order that will be added", required = true) @Valid @RequestBody Order order);

    @ApiOperation(value = "Get order by id", nickname = "getOrderById", response = Order.class,
            notes = "Retrieves a order by id",
            tags = {"order",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Order.class),
            @ApiResponse(code = 404, message = ExceptionMessage.OrderNotFound)
    })
    @GetMapping(
            value = "/{orderId}",
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<OrderResource> getOrderById(@ApiParam(value = "Customer id", example = "1") @Valid @PathVariable Integer customerId,
                                               @ApiParam(value = "Order id", example = "1") @Valid @PathVariable Integer orderId);

    @ApiOperation(value = "Get orders", nickname = "getOrder", response = Order.class, responseContainer = "List",
            notes = "Retrieves a collection of users by taking into account order features. Can be done pagination (quantity of items returned) and ordering",
            tags = {"order",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Order.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = ExceptionMessage.OrderNotFound)
    })
    @GetMapping(
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<PagedResources<Order>> getOrder(@ApiParam(value = "Customer id", example = "1") @Valid @PathVariable Integer customerId,
                                                   @ApiParam(value = "Order id", example = "1") @Valid @PathVariable @RequestParam(value = "id", required = false) Integer orderId,
                                                   @ApiParam(value = "Customer") @Valid @RequestParam(value = "customer", required = false) Customer customer,
                                                   @ApiParam(value = "Order value", example = "1.1") @Valid @RequestParam(value = "value", required = false) BigDecimal value,
                                                   @PageableDefault Pageable pageable,
                                                   PagedResourcesAssembler assembler);


    @ApiOperation(value = "Delete order", nickname = "deleteOrderById", tags = {"order",})
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = ExceptionMessage.OrderNotFound)
    })
    @DeleteMapping(
            value = "/{orderId}",
            consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    ResponseEntity<Void> deleteOrderById(@ApiParam(value = "Customer id of the order", example = "1", required = true) @Valid @PathVariable Integer customerId,
                                         @ApiParam(value = "Order id that will be deleted", example = "1", required = true) @Valid @PathVariable Integer orderId);

}
