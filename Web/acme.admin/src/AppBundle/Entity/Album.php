<?php

namespace AppBundle\Entity;

/**
 * Album
 */
class Album
{
    /**
     * @var string
     */
    private $nom;

    /**
     * @var \DateTime
     */
    private $lastUpdateTime;

    /**
     * @var integer
     */
    private $statut;

    /**
     * @var integer
     */
    private $id;

    /**
     * @var \AppBundle\Entity\Produit
     */
    private $produit;

    /**
     * @var \Doctrine\Common\Collections\Collection
     */
    private $utilisateur;

    /**
     * Constructor
     */
    public function __construct()
    {
        $this->utilisateur = new \Doctrine\Common\Collections\ArrayCollection();
        $this->lastUpdateTime = new \DateTime();
        $this->statut=1;
    }

    /**
     * Set nom
     *
     * @param string $nom
     *
     * @return Album
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
     * Set lastUpdateTime
     *
     * @param \DateTime $lastUpdateTime
     *
     * @return Album
     */
    public function setLastUpdateTime($lastUpdateTime)
    {
        $this->lastUpdateTime = $lastUpdateTime;

        return $this;
    }

    /**
     * Get lastUpdateTime
     *
     * @return \DateTime
     */
    public function getLastUpdateTime()
    {
        return $this->lastUpdateTime;
    }

    /**
     * Set statut
     *
     * @param integer $statut
     *
     * @return Album
     */
    public function setStatut($statut)
    {
        $this->statut = $statut;

        return $this;
    }

    /**
     * Get statut
     *
     * @return integer
     */
    public function getStatut()
    {
        return $this->statut;
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

    /**
     * Set produit
     *
     * @param \AppBundle\Entity\Produit $produit
     *
     * @return Album
     */
    public function setProduit(\AppBundle\Entity\Produit $produit = null)
    {
        $this->produit = $produit;

        return $this;
    }

    /**
     * Get produit
     *
     * @return \AppBundle\Entity\Produit
     */
    public function getProduit()
    {
        return $this->produit;
    }

    /**
     * Add utilisateur
     *
     * @param \AppBundle\Entity\Utilisateur $utilisateur
     *
     * @return Album
     */
    public function addUtilisateur(\AppBundle\Entity\Utilisateur $utilisateur)
    {
        $this->utilisateur[] = $utilisateur;

        return $this;
    }

    /**
     * Remove utilisateur
     *
     * @param \AppBundle\Entity\Utilisateur $utilisateur
     */
    public function removeUtilisateur(\AppBundle\Entity\Utilisateur $utilisateur)
    {
        $this->utilisateur->removeElement($utilisateur);
    }

    /**
     * Get utilisateur
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getUtilisateur()
    {
        return $this->utilisateur;
    }
}
