SELECT MIN(x.pk) AS pk, Feature.name, x.name
FROM FeatureValue x
    JOIN (SELECT p.featurePk
          FROM FeatureValue p
          GROUP BY p.featurePk) y 
    ON y.featurePk = x.featurePk
    JOIN Feature
    ON x.featurePk = Feature.pk
GROUP BY x.featurePk
