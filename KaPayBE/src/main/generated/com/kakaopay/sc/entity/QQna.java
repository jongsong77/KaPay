package com.kakaopay.sc.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QQna is a Querydsl query type for Qna
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQna extends EntityPathBase<Qna> {

    private static final long serialVersionUID = -80436869L;

    public static final QQna qna = new QQna("qna");

    public final StringPath agtAnswer = createString("agtAnswer");

    public final StringPath agtId = createString("agtId");

    public final StringPath agtName = createString("agtName");

    public final StringPath answeredAt = createString("answeredAt");

    public final StringPath createdAt = createString("createdAt");

    public final StringPath custId = createString("custId");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final StringPath inquiry = createString("inquiry");

    public final NumberPath<Integer> state = createNumber("state", Integer.class);

    public final StringPath title = createString("title");

    public QQna(String variable) {
        super(Qna.class, forVariable(variable));
    }

    public QQna(Path<? extends Qna> path) {
        super(path.getType(), path.getMetadata());
    }

    public QQna(PathMetadata metadata) {
        super(Qna.class, metadata);
    }

}

