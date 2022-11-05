package com.kakaopay.sc.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCust is a Querydsl query type for Cust
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCust extends EntityPathBase<Cust> {

    private static final long serialVersionUID = 1801014684L;

    public static final QCust cust = new QCust("cust");

    public final NumberPath<Long> ID = createNumber("ID", Long.class);

    public final StringPath userId = createString("userId");

    public QCust(String variable) {
        super(Cust.class, forVariable(variable));
    }

    public QCust(Path<? extends Cust> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCust(PathMetadata metadata) {
        super(Cust.class, metadata);
    }

}

