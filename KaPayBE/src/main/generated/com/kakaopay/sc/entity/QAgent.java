package com.kakaopay.sc.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAgent is a Querydsl query type for Agent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAgent extends EntityPathBase<Agent> {

    private static final long serialVersionUID = -5397284L;

    public static final QAgent agent = new QAgent("agent");

    public final StringPath agtId = createString("agtId");

    public final StringPath agtname = createString("agtname");

    public final StringPath agtPw = createString("agtPw");

    public final NumberPath<Long> ID = createNumber("ID", Long.class);

    public QAgent(String variable) {
        super(Agent.class, forVariable(variable));
    }

    public QAgent(Path<? extends Agent> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAgent(PathMetadata metadata) {
        super(Agent.class, metadata);
    }

}

