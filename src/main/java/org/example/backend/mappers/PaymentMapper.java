package org.example.backend.mappers;

import org.example.backend.dtos.PaymentDTO;
import org.example.backend.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMapper {

    @Mapping(source = "clientId.id", target = "clientId")
    @Mapping(source = "reservationId.id", target = "reservationId")
    PaymentDTO toPaymentDTO(Payment payment);

    @Mapping(source = "clientId", target = "clientId.id")
    @Mapping(source = "reservationId", target = "reservationId.id")
    Payment toPayment(PaymentDTO paymentDTO);

    List<PaymentDTO> toPaymentDTOList(List<Payment> payments);
    List<Payment> toPaymentList(List<PaymentDTO> paymentDTOList);
}
