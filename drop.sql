
    set client_min_messages = WARNING;

    alter table if exists automobile 
       drop constraint if exists FKb14xqlpgteyrp5tpgq8ncmmvo;

    alter table if exists bici 
       drop constraint if exists FKbwomtnan9xt4w1ovsf9sod7y1;

    alter table if exists moto 
       drop constraint if exists FKhg6x1bil1w6nnp4fxlwebfc3l;

    drop table if exists automobile cascade;

    drop table if exists bici cascade;

    drop table if exists categorie_automobili cascade;

    drop table if exists moto cascade;

    drop table if exists tipi_alimentazione_motorizzati cascade;

    drop table if exists tipo_freno cascade;

    drop table if exists tipo_sospensione cascade;

    drop table if exists veicolo cascade;
