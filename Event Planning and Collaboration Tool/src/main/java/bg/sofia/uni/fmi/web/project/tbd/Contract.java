//package bg.sofia.uni.fmi.web.project.model;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//
//import java.math.BigDecimal;
//
//@Entity
//public class Contract {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private long id;
//
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "event_id", nullable = false)
//    private Event event;
//
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "vendor_id", nullable = false)
//    private Vendor vendor;
//
//    @Column
//    private BigDecimal totalPrice;
//
//    @Column
//    private BigDecimal paymentStatus;
//
//    @Column
//    private ContractStatus contractStatus;
//}