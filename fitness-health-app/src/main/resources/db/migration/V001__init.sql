set
    quoted_identifier on

set nocount on

go

IF NOT EXISTS(SELECT *
              FROM sys.schemas
              WHERE name = N'fitness')
    EXEC ('CREATE SCHEMA fitness');

go

create table fitness.fitness_health_data
(
    id         bigint identity (1, 1) not null,
    user_id    bigint                 not null,
    client_id  bigint                 not null,
    device_id  nvarchar(200)          not null,
    provider   nvarchar(20)           null,
    data_type  nvarchar(9)            not null,
    created_at datetime default getutcdate(),

    constraint fitness_data_pk primary key (id)
);

go

create table fitness.hydration_data
(
    id                             bigint identity (1, 1) not null,
    day_total_water_consumption_ml int                    null,

    constraint hydration_data_pk primary key (id)
);

go

create table fitness.hydration_levels
(
    id                bigint identity (1, 1) not null,
    hydration_level   int                    null,
    timestamp         datetime               null,
    hydration_data_id bigint                 not null,

    constraint hydration_levels_pk primary key (id),
    constraint hydration_levels_hydration_data_fk foreign key (hydration_data_id) references fitness.hydration_data (id)
);

go

create table fitness.hydration_measurements
(
    id                bigint identity (1, 1) not null,
    hydration_data_id bigint                 not null,
    timestamp         datetime               null,
    hydration_kg      decimal(32, 16)        null,

    constraint hydration_measurements_pk primary key (id),
    constraint hydration_measurements_hydration_data_fk foreign key (hydration_data_id) references fitness.hydration_data (id)
);

go

create table fitness.oxygen_data
(
    id                        bigint identity (1, 1) not null,
    avg_saturation_percentage decimal(32, 16)        null,
    vo2max_ml_per_min_per_kg  decimal(32, 16)        null,

    constraint oxygen_data_pk primary key (id)
);

go

create table fitness.oxygen_saturations
(
    id             bigint identity (1, 1) not null,
    oxygen_data_id bigint                 not null,
    timestamp      datetime               null,
    percentage     decimal(32, 16)        null,

    constraint oxygen_saturations_pk primary key (id),
    constraint oxygen_saturations_oxygen_data_fk foreign key (oxygen_data_id) references fitness.oxygen_data (id)
);

go

create table fitness.oxygen_vo2
(
    id                       bigint identity (1, 1) not null,
    oxygen_data_id           bigint                 not null,
    timestamp                datetime               null,
    vo2max_ml_per_min_per_kg decimal(32, 16)        null,

    constraint oxygen_vo2_pk primary key (id),
    constraint oxygen_vo2_oxygen_data_fk foreign key (oxygen_data_id) references fitness.oxygen_data (id)
);

go

create table fitness.glucose_data
(
    id                              bigint identity (1, 1) not null,
    day_avg_blood_glucose_mg_per_dl decimal(32, 16)        null,

    constraint glucose_data_pk primary key (id)
);

go

create table fitness.blood_glucose_samples
(
    id                      bigint identity (1, 1) not null,
    glucose_data_id         bigint                 not null,
    type                    nvarchar(14)           null, --enum blood, detailed_blood
    timestamp               datetime               null,
    blood_glucose_mg_per_dl decimal(32, 16)        null,
    glucose_level_flag      int                    null,
    trend_arrow             int                    null,

    constraint blood_glucose_samples_pk primary key (id),
    constraint blood_glucose_samples_glucose_data_id_fk foreign key (glucose_data_id) references fitness.glucose_data (id)
);

go

create table fitness.body_fitness_health_data
(
    id                bigint not null,
    glucose_data_id   bigint null,
    oxygen_data_id    bigint null,
    hydration_data_id bigint null,
    constraint body_fitness_health_data_pk primary key (id),
    constraint body_fitness_health_data_fitness_health_data_fk foreign key (id) references fitness.fitness_health_data (id),
    constraint body_fitness_health_data_glucose_data_fk foreign key (glucose_data_id) references fitness.glucose_data (id),
    constraint body_fitness_health_data_oxygen_data_fk foreign key (oxygen_data_id) references fitness.oxygen_data (id),
    constraint body_fitness_health_data_hydration_data_fk foreign key (hydration_data_id) references fitness.hydration_data (id)
);

go

create table fitness.measurements
(
    id                          bigint identity (1, 1) not null,
    body_fitness_health_data_id bigint                 not null,
    measurement_time            datetime               null,
    bmi                         decimal(32, 16)        null,
    bmr                         decimal(32, 16)        null,
    rmr                         decimal(32, 16)        null,
    estimated_fitness_age       decimal(32, 16)        null,
    skin_fold_mm                decimal(32, 16)        null,
    bodyfat_percentage          decimal(32, 16)        null,
    weight_kg                   decimal(32, 16)        null,
    height_cm                   decimal(32, 16)        null,
    bone_mass_g                 decimal(32, 16)        null,
    muscle_mass_g               decimal(32, 16)        null,
    lean_mass_g                 decimal(32, 16)        null,
    water_percentage            decimal(32, 16)        null,
    insulin_units               decimal(32, 16)        null,
    insulin_type                nvarchar(200)          null,
    urine_color                 nvarchar(200)          null,

    constraint measurements_pk primary key (id),
    constraint measurements_body_fitness_health_data_fk foreign key (body_fitness_health_data_id) references fitness.body_fitness_health_data (id)
);

go

create table fitness.temperature_data
(
    id                          bigint identity (1, 1) not null,
    body_fitness_health_data_id bigint                 not null,
    type                        nvarchar(7)            null, --enum: body, ambient, skin
    timestamp                   datetime               null,
    temperature_celsius         decimal(32, 16)        null,

    constraint temperature_data_pk primary key (id),
    constraint temperature_data_body_fitness_health_data_fk foreign key (body_fitness_health_data_id) references fitness.body_fitness_health_data (id)
);

go

create table fitness.blood_pressure_data
(
    id                          bigint identity (1, 1) not null,
    body_fitness_health_data_id bigint                 not null,
    timestamp                   datetime               null,
    diastolic_bp                decimal(32, 16)        null,
    systolic_bp                 decimal(32, 16)        null,

    constraint blood_pressure_data_pk primary key (id),
    constraint blood_pressure_data_body_fitness_health_data_fk foreign key (body_fitness_health_data_id) references fitness.body_fitness_health_data (id)
);

go

create table fitness.nutrition_macros
(
    id              bigint identity (1, 1) not null,
    fat_g           decimal(32, 16)        null,
    trans_fat_g     decimal(32, 16)        null,
    fiber_g         decimal(32, 16)        null,
    carbohydrates_g decimal(32, 16)        null,
    protein_g       decimal(32, 16)        null,
    cholesterol_mg  decimal(32, 16)        null,
    sodium_mg       decimal(32, 16)        null,
    calories        decimal(32, 16)        null,
    sugar_g         decimal(32, 16)        null,
    alcohol_g       decimal(32, 16)        null,

    constraint nutrition_macros_pk primary key (id)
);

go

create table fitness.nutrition_micros
(
    id                  bigint identity (1, 1) not null,
    selenium_mg         decimal(32, 16)        null,
    niacin_mg           decimal(32, 16)        null,
    magnesium_mg        decimal(32, 16)        null,
    copper_mg           decimal(32, 16)        null,
    vitamin_b12_mg      decimal(32, 16)        null,
    vitamin_b6_mg       decimal(32, 16)        null,
    vitamin_c_mg        decimal(32, 16)        null,
    zinc_mg             decimal(32, 16)        null,
    vitamin_e_mg        decimal(32, 16)        null,
    manganese_mg        decimal(32, 16)        null,
    vitamin_d_mg        decimal(32, 16)        null,
    iodine_mg           decimal(32, 16)        null,
    chloride_mg         decimal(32, 16)        null,
    folate_mg           decimal(32, 16)        null,
    calcium_mg          decimal(32, 16)        null,
    molybdenum_mg       decimal(32, 16)        null,
    vitamin_a_mg        decimal(32, 16)        null,
    riboflavin_mg       decimal(32, 16)        null,
    folic_acid_mg       decimal(32, 16)        null,
    iron_mg             decimal(32, 16)        null,
    thiamin_mg          decimal(32, 16)        null,
    pantothenic_acid_mg decimal(32, 16)        null,
    caffeine_mg         decimal(32, 16)        null,
    vitamin_k_mg        decimal(32, 16)        null,
    chromium_mg         decimal(32, 16)        null,
    potassium_mg        decimal(32, 16)        null,
    biotin_mg           decimal(32, 16)        null,
    phosphorus_mg       decimal(32, 16)        null,

    constraint nutrition_micros_pk primary key (id)
);

go

create table fitness.nutrition_fitness_health_data
(
    id                  bigint          not null,
    water_ml            decimal(32, 16) null,
    nutrition_macros_id bigint          null,
    nutrition_micros_id bigint          null,

    constraint nutrition_fitness_health_data_pk primary key (id),
    constraint nutrition_fitness_health_data_fitness_health_data_fk foreign key (id) references fitness.fitness_health_data (id),
    constraint nutrition_fitness_health_data_nutrition_macros_fk foreign key (nutrition_macros_id) references fitness.nutrition_macros (id),
    constraint nutrition_fitness_health_data_nutrition_micros_fk foreign key (nutrition_micros_id) references fitness.nutrition_micros (id)
);

go

create table fitness.sleep_durations_awake
(
    id                                  bigint identity (1, 1) not null,
    duration_short_interruption_seconds decimal(32, 16)        null,
    duration_awake_state_seconds        decimal(32, 16)        null,
    duration_long_interruption_seconds  decimal(32, 16)        null,
    num_wakeup_events                   int                    null,
    wake_up_latency_seconds             decimal(32, 16)        null,
    num_out_of_bed_events               int                    null,
    sleep_latency_seconds               decimal(32, 16)        null,

    constraint sleep_durations_awake_pk primary key (id)
);

go

create table fitness.sleep_durations_asleep
(
    id                                 bigint identity (1, 1) not null,
    duration_light_sleep_state_seconds decimal(32, 16)        null,
    duration_asleep_state_seconds      decimal(32, 16)        null,
    num_rem_events                     int                    null,
    duration_rem_sleep_state_seconds   decimal(32, 16)        null,
    duration_deep_sleep_state_seconds  decimal(32, 16)        null,

    constraint sleep_durations_asleep_pk primary key (id),
);

go

create table fitness.sleep_fitness_health_data
(
    id                 bigint not null,
    awake_duration_id  bigint null,
    asleep_duration_id bigint null,

    constraint sleep_fitness_health_data_pk primary key (id),
    constraint sleep_fitness_health_data_fitness_health_data_fk foreign key (id) references fitness.fitness_health_data (id),
    constraint sleep_fitness_health_data_sleep_durations_asleep_fk foreign key (asleep_duration_id) references fitness.sleep_durations_asleep (id),
    constraint sleep_fitness_health_data_sleep_durations_awake_fk foreign key (awake_duration_id) references fitness.sleep_durations_awake (id)
);

go

create table fitness.stress_data
(
    id                               bigint identity (1, 1) not null,
    rest_stress_duration_seconds     decimal(32, 16)        null,
    stress_duration_seconds          decimal(32, 16)        null,
    activity_stress_duration_seconds decimal(32, 16)        null,
    avg_stress_level                 decimal(32, 16)        null,
    low_stress_duration_seconds      decimal(32, 16)        null,
    medium_stress_duration_seconds   decimal(32, 16)        null,
    high_stress_duration_seconds     decimal(32, 16)        null,
    max_stress_level                 decimal(32, 16)        null,

    constraint stress_data_pk primary key (id)
);

go

create table fitness.heart_rate_data
(
    id              bigint identity (1, 1) not null,
    max_hr_bpm      int                    null,
    resting_hr_bpm  int                    null,
    avg_hrv_rmssd   decimal(32, 16)        null,
    min_hr_bpm      int                    null,
    user_max_hr_bpm int                    null,
    avg_hrv_sdnn    decimal(32, 16)        null,
    avg_hr_bpm      int                    null,

    constraint heart_rate_data_pk primary key (id)
);

go


create table fitness.daily_scores
(
    id       bigint identity (1, 1) not null,
    recovery decimal(32, 16)        null,
    activity decimal(32, 16)        null,
    sleep    decimal(32, 16)        null,

    constraint daily_scores_pk primary key (id)
);

go

create table fitness.stress_samples
(
    id             bigint identity (1, 1) not null,
    stress_data_id bigint                 not null,
    timestamp      datetime               null,
    level          int                    null,

    constraint stress_samples_pk primary key (id),
    constraint stress_samples_stress_data_fk foreign key (stress_data_id) references fitness.stress_data (id)
);

go

create table fitness.daily_fitness_health_data
(
    id                 bigint not null,
    heart_rate_data_id bigint null,
    daily_score_id     bigint null,
    stress_data_id     bigint null,

    constraint daily_fitness_health_data_pk primary key (id),
    constraint daily_fitness_health_data_fitness_health_data_fk foreign key (id) references fitness.fitness_health_data (id),
    constraint daily_fitness_health_data_heart_rate_data_fk foreign key (heart_rate_data_id) references fitness.heart_rate_data (id),
    constraint daily_fitness_health_data_daily_scores_fk foreign key (daily_score_id) references fitness.daily_scores (id),
    constraint daily_fitness_health_data_stress_data_fk foreign key (stress_data_id) references fitness.stress_data (id)

);

go

create table fitness.distance_data
(
    id    bigint identity (1, 1) not null,
    steps int                    null,

    constraint distance_data_pk primary key (id)
);

go

create table fitness.active_duration_data
(
    id                              bigint identity (1, 1) not null,
    activity_seconds                decimal(32, 16)        null,
    rest_seconds                    decimal(32, 16)        null,
    low_intensity_seconds           decimal(32, 16)        null,
    vigorous_intensity_seconds      decimal(32, 16)        null,
    num_continuous_inactive_periods int                    null,
    inactivity_seconds              decimal(32, 16)        null,
    moderate_intensity_seconds      decimal(32, 16)        null,

    constraint active_durations_data_pk primary key (id)
);

go

create table fitness.activity_levels_samples
(
    id                      bigint identity (1, 1) not null,
    active_duration_data_id bigint                 not null,
    timestamp               datetime               null,
    level                   nvarchar(16)           null,

    constraint activity_levels_samples_pk primary key (id),
    constraint activity_levels_samples_active_durations_data_fk foreign key (active_duration_data_id) references fitness.active_duration_data (id)
);

go

create table fitness.calories_data
(
    id                    bigint identity (1, 1) not null,
    net_intake_calories   decimal(32, 16)        null,
    bmr_calories          decimal(32, 16)        null,
    total_burned_calories decimal(32, 16)        null,
    net_activity_calories decimal(32, 16)        null,

    constraint calories_data_pk primary key (id)
);

go

create table fitness.activity_fitness_health_data
(
    id                 bigint not null,
    distance_id        bigint null,
    active_duration_id bigint null,
    calories_data_id   bigint null,

    constraint activity_fitness_health_data_pk primary key (id),
    constraint activity_fitness_health_data_fitness_health_data_fk foreign key (id) references fitness.fitness_health_data (id),
    constraint activity_fitness_health_data_distance_data_fk foreign key (distance_id) references fitness.distance_data (id),
    constraint activity_fitness_health_data_active_duration_data_fk foreign key (active_duration_id) references fitness.active_duration_data (id),
    constraint activity_fitness_health_data_calories_data_fk foreign key (calories_data_id) references fitness.calories_data (id)
);

go

create table fitness.dead_letters
(
    id           bigint identity (1, 1) not null,
    data         nvarchar(max)          not null,
    error        nvarchar(500)          null,
    stack_trace  nvarchar(max)          null,
    processed    smallint               not null default 0,
    created_at   datetime               not null default getutcdate(),
    processed_at datetime               null,

    constraint dead_letters_pk primary key (id)
);

go

create nonclustered index ix_dead_letters_non_processed on fitness.dead_letters (processed) where processed = 0;

go
