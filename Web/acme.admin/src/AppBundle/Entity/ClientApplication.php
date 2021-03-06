<?php

namespace AppBundle\Entity;

/**
 * ClientApplication
 */
class ClientApplication
{
    /**
     * @var string
     */
    private $nom;

    /**
     * @var string
     */
    private $uidApplication;

    /**
     * @var integer
     */
    private $id;


    /**
     * Set nom
     *
     * @param string $nom
     *
     * @return ClientApplication
     */
    public function setNom($nom)
    {
        $this->nom = $nom;

        return $this;
    }

    /**
     * Get nom
     *
     * @return string
     */
    public function getNom()
    {
        return $this->nom;
    }

    /**
     * Set uidApplication
     *
     * @param string $uidApplication
     *
     * @return ClientApplication
     */
    public function setUidApplication($uidApplication)
    {
        $this->uidApplication = $uidApplication;

        return $this;
    }

    /**
     * Get uidApplication
     *
     * @return string
     */
    public function getUidApplication()
    {
        return $this->uidApplication;
    }

    /**
     * Get id
     *
     * @return integer
     */
    public function getId()
    {
        return $this->id;
    }
}
